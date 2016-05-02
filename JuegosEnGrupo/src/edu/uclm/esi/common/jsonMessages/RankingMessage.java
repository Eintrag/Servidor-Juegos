package edu.uclm.esi.common.jsonMessages;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class RankingMessage extends JSONMessage {
	@JSONable
	private JSONArray rankingEntries;

	public RankingMessage(JSONArray rankingEntries) {
		super(false);
		this.rankingEntries = rankingEntries;
	}

	public RankingMessage() {
		super(false);
		this.rankingEntries = new JSONArray();
	}

	public RankingMessage(JSONObject jso) {
		this();
		try {
			rankingEntries = jso.getJSONArray("rankingEntries");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void add(JSONObject jso) {
		rankingEntries.put(jso);
	}

	public int size() {
		return rankingEntries.length();
	}

	public JSONObject get(int i) {
		try {
			return rankingEntries.getJSONObject(i);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
}
