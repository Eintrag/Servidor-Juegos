package edu.uclm.esi.common.server.domain;

public class RankingEntry implements IRankingEntry{
// Leaf element
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
}
