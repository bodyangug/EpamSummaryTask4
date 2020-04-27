package ua.nure.aseev.SummaryTask4.db.entity;

/**
 * User entity.
 * 
 * @author B.Aseev
 * 
 */

public class User extends Entity {

	private static final long serialVersionUID = 1223;

	private String login;
	private String password;
	private String firstName;
	private String secondName;
	private String mail;
	private int role;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	@Override
	public String toString() {
		return "[login=" + login + ", firstName=" + firstName + ", secondName=" + secondName + ", mail=" + mail
				+ ", role=" + role + "]";
	}

}
