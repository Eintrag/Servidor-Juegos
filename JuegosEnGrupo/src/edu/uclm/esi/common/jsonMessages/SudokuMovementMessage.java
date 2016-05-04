package edu.uclm.esi.common.jsonMessages;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.JSONable;

public class SudokuMovementMessage extends JSONMessage {
	@JSONable
	private final int idGame=3;
	@JSONable
	private int row;
	@JSONable
	private int col;
	@JSONable
	private int value;
	@JSONable
	private int idUser;
	@JSONable
	private int idMatch;

	public SudokuMovementMessage(int row, int col, int value, int idUser, int idMatch) {
		super(true);
		this.row=row;
		this.col=col;
		this.value=value;
		this.idUser=idUser;
		this.idMatch=idMatch;
	}

	public SudokuMovementMessage(JSONObject jso) throws JSONException {
		this(jso.getInt("row"), jso.getInt("col"), jso.getInt("value"), jso.getInt("idUser"), jso.getInt("idMatch"));
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

	public int getUser() {
		return idUser;
	}

	public int getIdMatch() {
		return idMatch;
	}

	public int getIdGame() {
		return idGame;
	}
}
