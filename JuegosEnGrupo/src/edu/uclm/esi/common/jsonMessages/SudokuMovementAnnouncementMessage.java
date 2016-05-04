package edu.uclm.esi.common.jsonMessages;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.JSONable;

public class SudokuMovementAnnouncementMessage extends JSONMessage {
	@JSONable
	private int row;
	@JSONable
	private int col;
	@JSONable
	private int value;
	@JSONable
	private int idMatch;

	public SudokuMovementAnnouncementMessage(int row, int col, int value, int idMatch) {
		super(true);
		this.row=row;
		this.col=col;
		this.value=value;
		this.idMatch=idMatch;
	}

	public SudokuMovementAnnouncementMessage(JSONObject jso) throws JSONException {
		this(jso.getInt("row"), jso.getInt("col"), jso.getInt("value"), jso.getInt("idMatch"));
	}

	public int getCol() {
		return col;
	}

	public int getRow() {
		return row;
	}

	public int getValue() {
		return value;
	}

	public int getIdMatch() {
		return idMatch;
	}
}
