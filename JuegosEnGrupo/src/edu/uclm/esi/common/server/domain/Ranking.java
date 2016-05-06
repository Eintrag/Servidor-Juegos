package edu.uclm.esi.common.server.domain;

import java.util.ArrayList;
import java.util.List;

public class Ranking {
	private List<IRankingEntry> rankingEntries = new ArrayList<IRankingEntry>();

	public void addEntry(IRankingEntry rankingEntry) {
		this.rankingEntries.add(rankingEntry);
	}

	public List<IRankingEntry> getEntries() {
		return rankingEntries;
	}
	public void addEntry(String email, int victorias){
		this.rankingEntries.add(new RankingEntry(email, victorias));
	}
}
