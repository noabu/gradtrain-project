import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;
import java.util.Vector;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

 

public class TestFacebook {
		
	private static String[] columns = {"Name", "Study", "Work", "Live"};
	
	@Test
    public void loginApp() throws InterruptedException, IOException {
		int num=1;
		int rowNum = 1;
        Student st = new Student();
        MappingJson mj = new MappingJson("facebook");
        deleteFromFile(st);
        Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file
        
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        // Create a Sheet
        Sheet sheet = workbook.createSheet("Sheet1");
        // Create a Font for styling header cells
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Create a Row
        Row headerRow = sheet.createRow(0);

        // Create cells
        for(int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }
        
        //////////////////////////////////////////////////////////////////////////////   
        //create vector of facebook users to switch after 150 urls, to prevent blocking
        Vector<Users> facebookUsers=new Vector<>();
        String[] emails= {"nensital6@walla.co.il"};
        String[] pass= {"sheltal8"};
        for(int i =0 ; i<emails.length; i++) {
        	Users u=new Users();
            u.setName(emails[i]);
            u.setPassword(pass[i]);
            facebookUsers.add(u);
        }

        //set the chrome options to disable the location notification
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        System.setProperty("webdriver.chrome.driver", "C:\\javacc\\chromedriver.exe");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        //open a user facebook with email and password
        driver.get("http://facebook.com");
        driver.findElement(By.id("email")).sendKeys(facebookUsers.elementAt(0).getName());
        driver.findElement(By.id("pass")).sendKeys(facebookUsers.elementAt(0).getPassword());
        Thread.sleep(2000);
        driver.findElement(By.id("loginbutton")).click();
        Thread.sleep(2000);

        //enter the requested users to the vector:
        for(int i = 580; i<mj.getSizeOfFacebook(); i++)
        	st.addUrl("http://facebook.com/" + mj.getNumberOfFacebookByIndex(i) + '/');

        int index=1; //for the users vector -------> need to add check if the index > vector.size
        //open students facebook URL to get the name of their university
        for(int j = 0; j<st.getSizeUrl(); j++) {                          
        //switch facebook user after 150 enters.
        	if(j!=0 && j%150==0 && index<facebookUsers.size()) { // ---------------> change to j%150==0
        		//to click on the logout menu
        		driver.findElement(By.xpath("//div[@id='logoutMenu']")).click();
        		Thread.sleep(5000);
        		//to click on the logout button
        		driver.findElement(By.xpath("//div[@class='uiScrollableAreaContent']//li//*[text()='Log Out']")).click();
        		//driver.findElement(By.xpath("//li[@class='_54ni navSubmenu _6398 _64kz __MenuItem']//a")).click();
        		driver.findElement(By.id("email")).clear();
        		Thread.sleep(2000);
        		driver.findElement(By.id("email")).sendKeys(facebookUsers.elementAt(index).getName());
        		driver.findElement(By.id("pass")).sendKeys(facebookUsers.elementAt(index).getPassword());
        		Thread.sleep(2000);	
        		driver.findElement(By.id("loginbutton")).click();
        		Thread.sleep(2000);
        		index++;
        	}
        	Row row = sheet.createRow(rowNum++);
        	//open the The requested user by runing on the vector:
        	driver.get(st.getUrlByIndex(j));
        	//try to skip "Sorry, this content isn't available right now" page
        	//List<WebElement> myList3=driver.findElements(By.xpath("//*[text()[contains(.,'Sorry, this content isn't available right now')]]/a"));
        	List<WebElement> myList3=driver.findElements(By.xpath("//h2[contains(@class, 'uiHeaderTitle')]//i"));
        	System.out.println("1."+myList3.isEmpty());
        	if (!myList3.isEmpty()) {   
        		do {
        			j++;
        			driver.get(st.getUrlByIndex(j));
        			myList3=driver.findElements(By.xpath("//h2[contains(@class, 'uiHeaderTitle')]//i"));
        			System.out.println("2."+myList3.isEmpty());
        		}while(!myList3.isEmpty());
        	}
        	//get the name of the student
        	String studentName = driver.findElement(By.cssSelector("#fb-timeline-cover-name>a")).getText();
        	st.setStudentName(studentName);
        	row.createCell(0).setCellValue(studentName);
        	//row.createCell(0).setCellValue(studentName);
        	//--get the name of the universities where he\she studied: --//
        	//1. if the user studied(in the past):
        	//to catch all web elements into list
        	List<WebElement> myList=driver.findElements(By.xpath("//*[text()[contains(.,'Studie')]]/a"));
        	//myList contains all the web elements
        	//if you want to get all elements text into array list
        	String str = "";
        	for(int i=0; i<myList.size(); i++){
        		//loading text of each element in to array.
        		st.addUniversity(myList.get(i).getText());
        		Thread.sleep(3000);
        		str = str + myList.get(i).getText() + ", ";
        	}
        	//2. if the user studying(in the present)
        	List<WebElement> myList2=driver.findElements(By.xpath("//*[text()[contains(.,'Studieþ')]]/a"));
        	for(int i=0; i<myList2.size(); i++){
        		//loading text of each element in to array
        		st.addUniversity(myList2.get(i).getText());
        		Thread.sleep(3000);
        		str = str + myList.get(i).getText() + "/n";
        	}
        	
        	row.createCell(1).setCellValue(str);

        	//--get the student address --//
        	List<WebElement> myList4=driver.findElements(By.xpath("//*[text()[contains(.,'Lives')]]/a"));
        	for(int i=0; i<myList4.size(); i++){
        		//loading text of each element in to array
        		st.addAddress(myList4.get(i).getText());
        		Thread.sleep(3000);
        		row.createCell(2).setCellValue(myList4.get(i).getText());
        	}

        	//--get the student work place --//
        	List<WebElement> myList5=driver.findElements(By.xpath("//i[@class = '_3-90 _8o _8s lfloat _ohe img sp_PcNl_Pzo88k_1_5x sx_5e4a18']/../div//div//div//a"));
        	System.out.println(myList5.size());
        	for(int i=0; i<myList5.size(); i++){
        		//loading text of each element in to array
        		st.addWork(myList5.get(i).getText());
        		Thread.sleep(3000);
        		row.createCell(3).setCellValue(myList5.get(i).getText());
        	}
        	
            for(int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            // Write the output to a file
            FileOutputStream fileOut = new FileOutputStream("TestExcel580.xlsx");
            workbook.write(fileOut);
            fileOut.close();

        	writeToFile(st,num);
        	num++;
        	Thread.sleep(6000);
        	Thread.sleep(6000);
        	Thread.sleep(6000);
        }
     // Closing the workbook
        workbook.close();
        Thread.sleep(6000);
        driver.quit();
	}
	// private func that write to file the name of the student and is university name
	private void writeToFile(Student st, int num) {
		File log = new File("SwithU.txt");
		try{
			if(log.exists()==false){
				System.out.println("We had to make a new file.");
				log.createNewFile();
			}
			PrintWriter out = new PrintWriter(new FileWriter(log, true));
			String res="";
			res=Integer.toString(num)+". "+st.sAnduNames();
			out.println(res);
			out.close();
		}catch(IOException e){
			System.out.println("COULD NOT WRITE!!");
		}
		st.deleteUniversityList();
		st.deleteAddressList();
		st.deleteWorkList();
	}
    
	//clear the data in the file from the last running
	private void deleteFromFile(Student st){
		FileWriter fwOb;
		try {
			fwOb = new FileWriter("SwithU.txt", false);
			PrintWriter pwOb = new PrintWriter(fwOb, false);
			pwOb.flush();
			pwOb.close();
			fwOb.close();
		} catch (IOException e) {
			e.printStackTrace();
        }
	}
	
}