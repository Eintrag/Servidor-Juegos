package edu.uclm.esi.common.server.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOPartidaForTest extends DAOPartida{
	// This class is only for test, has methods that shouldnt be in DAOPartida
	private static Connection bd;
	public static void dropTable() throws SQLException{
			bd = getConnection();
			String sql = "DROP TABLE Partida;";
			PreparedStatement ps=bd.prepareStatement(sql);
			ps.execute();
			
			//database is in autocommit mode
	}
	
	public static void createTable(){
		try {
			bd = getConnection();
			String sql = "CREATE TABLE PARTIDA (idPartida  INTEGER NOT NULL PRIMARY KEY, ganadoremail varchar(255) NOT NULL, perdedoremail varchar(255) NOT NULL, tiemposegs integer(10) NOT NULL);";
			PreparedStatement ps=bd.prepareStatement(sql);
			ps.execute();
			
			//database is in autocommit mode
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private static Connection getConnection(){
		bd = null;
		try {
			bd = BrokerWithRankingDB.get().getRankingDB();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return bd;
	}
}
