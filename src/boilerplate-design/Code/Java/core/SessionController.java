package core;

import java.io.Serializable;

import core.user.UserController;
import core.user.UserModel;

public class SessionController implements Serializable {
	private static final long serialVersionUID = 1L;

	public void InitUserSession() {
		user = UserController.InitCurrentUser();
	}

	// VARIABLES
	UserModel user;

	// GETTESR AND SETTERS
	public UserModel getUser() {
		return user;
	}
}