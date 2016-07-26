package core.user;

import java.io.Serializable;
import java.util.Vector;

public class UserModel implements Serializable {
	private static final long serialVersionUID = 1L;

	// CONSTRUCTOR
	public UserModel() {
		_InitModel();
	}

	// VARIABLES
	// TODO: Review all the properties to see what's valid
	public String id;
	public String firstName;
	public String lastName;
	public String commonName;
	public String canonicalName;
	public String email;
	public String gender;
	public String username;
	public String idNumber;
	public Vector<String> orgStructure;
	public boolean forceEmail;
	public String password;
	public String confirmPassword;
	public boolean rememberMe;
	public String twitterToken;
	public String facebookToken;
	public String linkedInToken;
	public boolean isAdmin;
	public boolean isDebug;

	// PRIVATE METHODS
	private void _InitModel() {
		id = "";
		firstName = "";
		lastName = "";
		commonName = "";
		canonicalName = "";
		email = "";
		gender = "";
		username = "";
		idNumber = "";
		orgStructure = new Vector<String>();
		forceEmail = false;
		password = "";
		confirmPassword = "";
		rememberMe = false;
		twitterToken = "";
		facebookToken = "";
		linkedInToken = "";
		isAdmin = false;
		isDebug = false;
	}

	// GETTERS AND SETTERS
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isForceEmail() {
		return forceEmail;
	}

	public void setForceEmail(boolean forceEmail) {
		this.forceEmail = forceEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	public String getTwitterToken() {
		return twitterToken;
	}

	public void setTwitterToken(String twitterToken) {
		this.twitterToken = twitterToken;
	}

	public String getFacebookToken() {
		return facebookToken;
	}

	public void setFacebookToken(String facebookToken) {
		this.facebookToken = facebookToken;
	}

	public String getLinkedInToken() {
		return linkedInToken;
	}

	public void setLinkedInToken(String linkedInToken) {
		this.linkedInToken = linkedInToken;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public Vector<String> getOrgStructure() {
		return orgStructure;
	}

	public void setOrgStructure(Vector<String> orgStructure) {
		this.orgStructure = orgStructure;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public boolean isDebug() {
		return isDebug;
	}

	public void setDebug(boolean isDebug) {
		this.isDebug = isDebug;
	}

	public String getCanonicalName() {
		return canonicalName;
	}

	public void setCanonicalName(String canonicalName) {
		this.canonicalName = canonicalName;
	}
}
