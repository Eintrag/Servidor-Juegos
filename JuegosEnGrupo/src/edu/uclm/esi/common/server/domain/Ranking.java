package edu.uclm.esi.common.server.domain;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class Ranking {
	// implements the composite pattern
	private List<RankingEntry> rankingEntries = new ArrayList<RankingEntry>();

	public void addEntry(RankingEntry rankingEntry) {
		this.rankingEntries.add(rankingEntry);
	}

	public List<RankingEntry> getRankingEntries() {
		return rankingEntries;
	}
	
	public JSONObject toJSON() throws JSONException {
		JSONObject jso=new JSONObject();
		
		for (int i = 0; i<10;i++)
		{
			if (i < rankingEntries.size()){
				jso.put("type", "Ranking");
				jso.put("email" + String.valueOf(i+1), rankingEntries.get(i).getEmailganador());
				jso.put("victorias"+String.valueOf(i+1), rankingEntries.get(i).getNumVictorias());
			} else {
				jso.put("type", "Ranking");
				jso.put("email" + String.valueOf(i+1), "");
				jso.put("victorias"+String.valueOf(i+1), 0);
			}
			
		}
		return jso;
	}
}
