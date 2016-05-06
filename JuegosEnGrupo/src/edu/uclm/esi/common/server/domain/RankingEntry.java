package edu.uclm.esi.common.server.domain;

import org.json.JSONException;
import org.json.JSONObject;

public class RankingEntry implements IRankingEntry{

	private String emailGanador;
	private int numVictorias;
	public RankingEntry(String emailganador, int numVictorias){
		this.emailGanador = emailganador;
		this.numVictorias = numVictorias;
	}
	public String getEmailganador(){
		return emailGanador;
	}
	public int getNumVictorias(){
		return numVictorias;
	}
	public JSONObject toJSON() throws JSONException {
		JSONObject jso=new JSONObject();
		jso.put("emailGanador", this.emailGanador);
		jso.put("numVictorias", this.numVictorias);
		return jso;
	}
}
