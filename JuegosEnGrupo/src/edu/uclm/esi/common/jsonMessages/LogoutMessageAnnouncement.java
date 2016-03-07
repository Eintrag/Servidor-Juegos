package edu.uclm.esi.common.jsonMessages;

public class LogoutMessageAnnouncement extends JSONMessage{
	String email;
	public LogoutMessageAnnouncement(String email) {
		this.email = email;
	}

}
