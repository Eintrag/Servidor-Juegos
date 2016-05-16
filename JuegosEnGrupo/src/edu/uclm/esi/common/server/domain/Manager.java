package edu.uclm.esi.common.server.domain;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Vector;

import com.maco.juegosEnGrupo.server.dominio.Game;
import com.maco.juegosEnGrupo.server.dominio.Match;
import com.maco.juegosEnGrupo.server.modelo.DatosJugadorFicticio;

import edu.uclm.esi.common.jsonMessages.LoginMessageAnnouncement;
import edu.uclm.esi.common.server.domain.User;
import edu.uclm.esi.common.server.persistence.Broker;
import edu.uclm.esi.common.server.persistence.DAOPartida;

public class Manager {
	private static Manager yo;

	private Hashtable<String, User> usersByEmail;
	private Hashtable<Integer, User> usersById;
	private Hashtable<Integer, Game> games;

	private Manager() {
		this.usersByEmail = new Hashtable<String, User>();
		this.usersById = new Hashtable<Integer, User>();
		this.games = new Hashtable<Integer, Game>();
		try {
			this.findAllGames();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Manager get() {
		if (yo == null)
			yo = new Manager();
		return yo;
	}

	public void add(User user, String ip) throws IOException {
		if (usersByEmail.get(user.getEmail()) != null) {
			usersByEmail.remove(user.getEmail());
			usersById.remove(user.getId());
		}
		user.setIp(ip);
		// avisar al resto de jugadores:
		LoginMessageAnnouncement lm = new LoginMessageAnnouncement(user.getEmail());
		for (User player : usersById.values()) {
			player.addMensajePendiente(lm);
		}
		usersByEmail.put(user.getEmail(), user);
		usersById.put(user.getId(), user);

		if (user.getEmail() != DatosJugadorFicticio.getEmail()) {
			Thread thread = new Thread(new JugadorFicticioRunnable());
			thread.start();
		}
	}

	public User findUserByEmail(String email) {
		return this.usersByEmail.get(email);
	}

	public User closeSession(User user) throws SQLException {
		user = usersById.remove(user.getId());
		if (user != null) {
			user.getDB().close();
		}
		return usersByEmail.remove(user.getEmail());
	}

	public User findUserById(int id) {
		return this.usersById.get(id);
	}

	public Vector<Game> findAllGames() throws SQLException {
		Connection bd = Broker.get().getDBPrivilegiada();
		Vector<Game> result = new Vector<Game>();
		String sql = "Select id, name, playersMin, playersMax from Game order by name";
		PreparedStatement ps = bd.prepareStatement(sql);
		ResultSet r = ps.executeQuery();
		while (r.next()) {
			Game g = new Game();
			g.setId(r.getInt(1));
			g.setName(r.getString(2));
			g.setPlayersMin(r.getInt(3));
			g.setPlayersMax(r.getInt(4));
			result.add(g);
			if (this.games.get(g.getId()) == null)
				this.games.put(g.getId(), g);
		}
		// TODO add in db?
		// add sudoku
		Game g = new Game();
		g.setId(0);
		g.setName("Sudoku");
		g.setPlayersMin(2);
		g.setPlayersMax(2);
		result.add(g);
		if (this.games.get(g.getId()) == null)
			this.games.put(g.getId(), g);
		ps.close();
		return result;
	}

	public int add(int idGame, int idUser) throws Exception {
		Game g = this.games.get(idGame);
		if (g == null)
			throw new Exception("Unknown game");
		User user = this.findUserById(idUser);
		g.add(user);
		Match pendingMatch = g.findPendingMatch();
		if (pendingMatch == null) {
			pendingMatch = Match.build(g);
		}
		pendingMatch.add(user);
		g.add(pendingMatch);
		return pendingMatch.hashCode();
	}

	public void vigilaUser(User user, Match match) {
		Thread thread = new Thread(new Vigilante(user, match));
		thread.start();
	}

	public Game findGameById(int id) {
		return this.games.get(id);
	}

	public Ranking getRanking() {

		Ranking ranking = null;
		try {
			ranking = DAOPartida.getTopTen();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ranking;

	}

	public void concludeGame(User winner, Match match) {
		 match.concludeGame(winner);
	}
}
