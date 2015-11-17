package model;

import java.util.List;

public class User {
	private String username;
	private String hashUsername;
	private String password;
	private String hashPassword;
	private List<SubAccount> subAccount;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getHashUsername() {
		return hashUsername;
	}
	public void setHashUsername(String hashUsername) {
		this.hashUsername = hashUsername;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHashPassword() {
		return hashPassword;
	}
	public void setHashPassword(String hashPassword) {
		this.hashPassword = hashPassword;
	}
	public List<SubAccount> getSubAccount() {
		return subAccount;
	}
	public void setSubAccount(List<SubAccount> subAccount) {
		this.subAccount = subAccount;
	}
	
	
}
