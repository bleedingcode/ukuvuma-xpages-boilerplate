package core;

import java.io.Serializable;

public class LoginModel implements Serializable {
	private static final long serialVersionUID = 1L;

	// CONSTRUCTOR
	public LoginModel() {
		_InitModel();
	}

	// VARIABLES
	public FormHeaderModel header;
	public String username;
	public String password;

	// PRIVATE METHODS
	private void _InitModel() {
		header = new FormHeaderModel();
		username = "";
		password = "";
	}

	// GETTERS AND SETTERS
	public FormHeaderModel getHeader() {
		return header;
	}

	public void setHeader(FormHeaderModel header) {
		this.header = header;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
