package edu.uclm.esi.common.server.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BrokerWithRankingDB {
	private String url = "jdbc:sqlite:test.db";
	private static BrokerWithRankingDB yo;

	private BrokerWithRankingDB() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		}
	}

	public static BrokerWithRankingDB get() {
		if (yo == null)
			yo = new BrokerWithRankingDB();
		return yo;
	}

	public Connection getRankingDB() throws SQLException {
		return DriverManager.getConnection(url);
	}

}
