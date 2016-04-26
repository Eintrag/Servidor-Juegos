package edu.uclm.esi.common.server.domain;

import java.util.ArrayList;
import java.util.List;

public class Ranking {
	// implements the composite pattern
	private List<RankingEntry> rankingEntries = new ArrayList<RankingEntry>();

	public void addEntry(RankingEntry rankingEntry) {
		this.rankingEntries.add(rankingEntry);
	}

	public List<RankingEntry> getRankingEntries() {
		return rankingEntries;
	}
}
