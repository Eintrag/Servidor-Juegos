package edu.uclm.esi.common.server.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.uclm.esi.common.server.domain.Ranking;
import edu.uclm.esi.common.server.domain.RankingEntry;

public class DAOPartida {
	private static Connection bd;
	public static void addPartida(String ganadoremail, String perdedoremail, int tiemposegs) {
		try {
			bd = getConnection();
			String sql = "INSERT INTO PARTIDA (ganadoremail, perdedoremail, tiemposegs) " + "VALUES (?, ?, ?);";
			PreparedStatement ps = bd.prepareStatement(sql);
			ps.setString(1, ganadoremail);
			ps.setString(2, perdedoremail);
			ps.setInt(3, 30);
			ps.execute();

			// database is in autocommit mode
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static int getVictorias(String email) {
		Connection bd;
		int victorias = -1;
		try {
			bd = getConnection();
			String sql = "SELECT count(1) FROM PARTIDA where ganadoremail = ?;";
			PreparedStatement ps = bd.prepareStatement(sql);
			ps.setString(1, email);
			ResultSet r = ps.executeQuery();
			victorias = r.getInt(1);
			r.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return victorias;
	}

	public static Ranking getTopTen() {
		Ranking ranking = new Ranking();
		try {
			bd = getConnection();
			String sql = "Select ganadoremail, count(1) from PARTIDA group by ganadoremail ORDER BY count(1) DESC";
			PreparedStatement ps = bd.prepareStatement(sql);
			ResultSet r = ps.executeQuery();

			while (r.next()) {
				RankingEntry rankingEntry = new RankingEntry(r.getString(1), r.getInt(2));
				ranking.addEntry(rankingEntry);
			}
			ps.close();
		} catch (SQLException e) {

		}
		return ranking;
	}

	private static Connection getConnection() {
		try {
			bd = BrokerWithRankingDB.get().getRankingDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bd;
	}
}
