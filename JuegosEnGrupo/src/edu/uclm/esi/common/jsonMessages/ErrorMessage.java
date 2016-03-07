package edu.uclm.esi.common.jsonMessages;

public class ErrorMessage extends JSONMessage{
	String message;
	public ErrorMessage(String message) {
		this.message = message;
	}

}
