package com.admin.snapandsplit.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
	private String accessToken;
	private String displayName;
	private String mobileNumber;
    private String accountNumber;
	private String email;
	private String profileImageURL;
	private String provider;
   // private Object transactions;
	private String uid;
	private List<String> rides = new ArrayList<>();



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

	public String getMobileNumber() { return mobileNumber;}

	public  void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber;}

    public String getAccountNumber() { return accountNumber;}

    public  void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber;}

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

  /*  public Object getTransactions() {
        return transactions;
    }

    public void setTransactions(Object transactions) {
        this.transactions = transactions;
    }*/

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public List<String> getRides() {
		return rides;
	}

	public void setRides(List<String> rides) {
		this.rides = rides;
	}
}
