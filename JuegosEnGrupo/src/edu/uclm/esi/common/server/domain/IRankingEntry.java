package edu.uclm.esi.common.server.domain;

import org.json.JSONException;
import org.json.JSONObject;

public interface IRankingEntry {
	public String getEmailganador();
	public int getNumVictorias();
	public JSONObject toJSON() throws JSONException;
}
