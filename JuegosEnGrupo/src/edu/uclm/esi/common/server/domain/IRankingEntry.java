package edu.uclm.esi.common.server.domain;

import org.json.JSONException;
import org.json.JSONObject;

public interface IRankingEntry {
// Component element
	public String getEmailganador();
	public int getNumVictorias();
	public JSONObject toJSON() throws JSONException;
}
