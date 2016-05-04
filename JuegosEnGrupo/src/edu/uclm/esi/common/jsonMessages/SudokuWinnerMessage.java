package edu.uclm.esi.common.jsonMessages;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.JSONable;

public class SudokuWinnerMessage extends JSONMessage {
	@JSONable 
	private String userWinner;
	@JSONable
	private long time;
	@JSONable
	private int idMatch;

	public SudokuWinnerMessage(String userWinner, long time, int idMatch) {
		super(false);
		this.userWinner=userWinner;
		this.time=time;
		this.idMatch=idMatch;
	}
	
	public SudokuWinnerMessage(JSONObject jso) throws JSONException {
		this(jso.getString("userWinner"), jso.getLong("time"), jso.getInt("idMatch"));
	}

	public String getUser1() {
		return userWinner;
	}
	
	public long getTime() {
		return time;
	}
	
	public int getIdMatch() {
		return idMatch;
	}
}
