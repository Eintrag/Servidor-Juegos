package edu.uclm.esi.common.jsonMessages;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uclm.esi.common.server.domain.RankingEntry;

public class RankingMessage extends JSONMessage {
	@JSONable
	private List<RankingEntry> rankingEntries;
	
	public RankingMessage(List <RankingEntry> rankingEntries){
		super(false);
		this.rankingEntries = rankingEntries;
	}
	
	public RankingMessage (JSONObject jso) throws JSONException {

		this(rellenarRanking(jso));
	}
	
	//Crea la lista de rankingEntries que se le pasará al constructor.
	private static List <RankingEntry> rellenarRanking (JSONObject jso) throws JSONException{
		List <RankingEntry> rankingEntriesAux = new ArrayList <RankingEntry>();
		
		for (int i = 0; i<10;i++)
		{
			rankingEntriesAux.add(i, new RankingEntry(jso.get(("email" + String.valueOf(i+1))).toString(),Integer.parseInt(jso.get("victorias"+String.valueOf(i+1)).toString())));
		}
		return rankingEntriesAux;
	}
	
	public List <RankingEntry> getRankingEntries() {
		return rankingEntries;
	}

	public void setRankingEntries(List <RankingEntry> rankingEntries) {
		this.rankingEntries = rankingEntries;
	}
}
