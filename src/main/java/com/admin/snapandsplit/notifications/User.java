package com.admin.snapandsplit.notifications;

import java.util.ArrayList;
import java.util.List;


public class User {
	private String accessToken;
	private String displayName;
	private String MobileNumber;
	private String email;
	private String profileImageURL;
	private String provider;
	private String uid;
	private List<String> rides = new ArrayList<>();
	private boolean selected;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getMobileNumber() { return MobileNumber;}

	public  void setMobileNumber(String MobileNumber) { this.MobileNumber = MobileNumber;}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProfileImageURL() {
		return profileImageURL;
	}

	public void setProfileImageURL(String profileImageURL) {
		this.profileImageURL = profileImageURL;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	/*public List<String> getRides() {
		return rides;
	}

	public void setRides(List<String> rides) {
		this.rides = rides;
	}
*/
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
