package edu.uclm.esi.common.jsonMessages;

import org.json.JSONArray;

public class OKMessage extends JSONMessage{
	JSONArray jsa;
	public OKMessage(JSONArray jsa) {
		this.jsa = jsa;
	}
	public OKMessage() {
		// TODO Auto-generated constructor stub
	}

}
