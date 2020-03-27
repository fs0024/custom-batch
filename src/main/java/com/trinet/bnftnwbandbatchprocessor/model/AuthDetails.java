/**
 * 
 */
package com.trinet.bnftnwbandbatchprocessor.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author imistry1
 *
 * 
 */
public class AuthDetails {
	
	@JsonProperty("guid")
	private String guid;

	@JsonProperty("userpassword")
	private String userpassword;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getUserpassword() {
		return userpassword;
	}

	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}

}
