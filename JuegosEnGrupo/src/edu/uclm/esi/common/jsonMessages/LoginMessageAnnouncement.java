package edu.uclm.esi.common.jsonMessages;

public class LoginMessageAnnouncement extends JSONMessage{
	String email;
	public LoginMessageAnnouncement(String email) {
		this.email = email;
	}

}
