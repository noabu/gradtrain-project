
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

public class TestLinkedin {

	@Test
	public void loginApp() throws InterruptedException, IOException {
		int num=1;
		Student st = new Student();
		MappingJson mj = new MappingJson("linkedin");
		deleteFromFile(st);
		
		//set the chrome options to disable the location notification
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("profile.default_content_setting_values.notifications", 2);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", prefs);
		System.setProperty("webdriver.chrome.driver", "C:\\javacc\\chromedriver.exe");
		WebDriver driver = new ChromeDriver(options);
		driver.manage().window().maximize();

		//open a user facebook with email and password

		driver.get("https://www.linkedin.com");
		driver.findElement(By.id("login-email")).sendKeys("yulilevi2@walla.co.il");
		driver.findElement(By.id("login-password")).sendKeys("leviyuli2");
		Thread.sleep(2000);
		driver.findElement(By.id("login-submit")).click();
		Thread.sleep(4000);
		driver.get("https://www.linkedin.com/in/virginieletard/");
		Thread.sleep(6000);
		
		//enter the requested users to the vector:

		for(int i = 0; i<mj.getSizeOfLinklen(); i++)
			st.addUrl("http://linkedin.com/in/" + mj.getNumberOfLinkedinByIndex(i) + '/');

		for(int j = 0; j<st.getSizeUrl(); j++) {
			driver.get(st.getUrlByIndex(j));
			Thread.sleep(3000);

			//try to skip "Sorry, this content isn't available right now" page
			//List<WebElement> myList3=driver.findElements(By.xpath("//*[text()[contains(.,'Sorry, this content isn't available right now')]]/a"));
			List<WebElement> myList3=driver.findElements(By.xpath("//div[@class='profile-unavailable']"));
			System.out.println("1."+myList3.isEmpty());
			if (!myList3.isEmpty()) {   
				do {
					j++;
					driver.get(st.getUrlByIndex(j));
					myList3=driver.findElements(By.xpath("//div[@class='profile-unavailable']"));
					System.out.println("2."+myList3.isEmpty());
				}while(!myList3.isEmpty());
			}
			
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,500)");
			String studentName = driver.findElement(By.xpath("//*[@class ='pv-top-card-section__name inline t-24 t-black t-normal']")).getText();
			st.setStudentName(studentName);

			List<WebElement> education = driver.findElements(By.xpath("//*[@id='education-section'] //ul//li//h3"));
			System.out.println(education.size());
			
			for (int i = 0; i < education.size(); i++) {
				st.addUniversity(education.get(i).getText());
				Thread.sleep(3000);
			} 
			Thread.sleep(3000);

			List<WebElement> experience = driver.findElements(By.xpath("//*[text()[contains(.,'Company Name')]]/../span[2]"));
			for (int i = 0; i < experience.size(); i++) {
				st.addWork(experience.get(i).getText());
				Thread.sleep(3000);
			}

			List<WebElement> address = driver.findElements(By.xpath("//h3[@class='pv-top-card-section__location t-16 t-black--light t-normal mt1 inline-block']"));
			for (int i = 0; i < address.size(); i++) {
				st.addAddress(address.get(i).getText());
				Thread.sleep(3000);
			}

			writeToFile(st,num);
			num++;
			Thread.sleep(6000);
		}
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

