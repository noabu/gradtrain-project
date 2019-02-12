
//this class save facebook userName and password, it will be used in "TestFacebook" class in vector that will keep 7 users to prevent blocking.

public class Users {

	private String name;
	private String password;

	public Users() {
		name="";
		password="";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}

