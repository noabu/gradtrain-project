import java.util.Vector;



public class Student {
	private String studentName;
	private Vector<String> universityName;
	private Vector<String> urls;
	private Vector<String> address;
	private Vector<String> work;
	private int numOfUniversies;
	private int numOfUrls;
	private int numOfAddress;
	private int numOfWork;

	public Student() {
		studentName = "";
		universityName=new Vector<>();
		urls = new Vector<>();
		address= new Vector<>();
		work= new Vector<>();
		numOfUniversies = 0;
		numOfUrls = 0;
		numOfWork=0;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public int getSizeUni() {
		return numOfUniversies;
	}

	public int getSizeUrl() {
		return numOfUrls;
	}

	public int getSizeAddress() {
		return numOfAddress;
	}

	public int getSizeWork() {
		return numOfWork;
	}

	public void addUniversity(String s) {
		universityName.add(s);
		numOfUniversies++;
	}

	public void addUrl(String url) {
		urls.add(url);
		numOfUrls++;
	}

	public void addAddress(String a) {
		address.add(a);
		numOfAddress++;
	}

	public void addWork(String w) {
		work.add(w);
		numOfWork++;
	}

	private String getList() {
		int i = 0;
		if(universityName.isEmpty())
			return "The user doesn't specify where he/she is studying. " + "\n";
		String res = "";
		for(String s : universityName ) {
			if(i == universityName.size()-1)
				res += s + ". " + "\n";
			else
				res += s + ", ";
			i++;
		}
		return res;
	}



	private String getListOfAddress() {
		int i = 0;
		if(address.isEmpty())
			return "The user doesn't specify where he/she is living. " + "\n";
		String res = "";
		for(String s : address ) {
			if(i == address.size()-1)
				res += s + ". " + "\n";
			else
				res += s + ", ";
			i++;
		}
		return res;
	}



	private String getListOfWorks() {
		int i = 0;
		if(work.isEmpty())
			return "The user doesn't specify where he/she is work/ed. " + "\n";
		String res = "";
		for(String s : work ) {
			if(i == work.size()-1)
				res += s + ". " + "\n";
			else
				res += s + ", ";
			i++;
		}
		return res;
	}

	public String sAnduNames() {
		String res = "";
		res = this.studentName + "-\n  Studies/d at: "+this.getList()+" Lives in: " +this.getListOfAddress();
		res= res+ "work at: "+ this.getListOfWorks();
		return res;
	}

	public String getUrlByIndex(int index) {
		return urls.get(index);
	}

	public void deleteUniversityList(){
		universityName.clear();
	}

	public void deleteAddressList(){
		address.clear();
	}

	public void deleteWorkList(){
		work.clear();
	}

	public Vector<String> getAddress() {
		return address;
	}

	public void setAddress(Vector<String> address) {
		this.address = address;
	}

}