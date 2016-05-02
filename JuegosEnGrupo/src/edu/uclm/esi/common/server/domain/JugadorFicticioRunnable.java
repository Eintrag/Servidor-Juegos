package edu.uclm.esi.common.server.domain;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;

import com.maco.juegosEnGrupo.server.dominio.Match;
import com.maco.juegosEnGrupo.server.dominio.Sudoku;
import com.maco.juegosEnGrupo.server.modelo.DatosJugadorFicticio;
import com.maco.juegosEnGrupo.server.modelo.SudokuBoardsEnum;

import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.SudokuBoardMessage;

public class JugadorFicticioRunnable implements Runnable {
	private User jugadorFicticio;
	private Sudoku match;
	private int matchId;
	private String lastBoard;
	private String solvedBoard;

	private void identificarse() {
		try {
			Connection bd;
			bd = User.identify(DatosJugadorFicticio.getEmail(), DatosJugadorFicticio.getPassword());
			jugadorFicticio = new User(bd, DatosJugadorFicticio.getEmail(), "User2016");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private Match entrarEnPartida() {
		Manager manager = Manager.get();
		try {
			manager.add(jugadorFicticio, InetAddress.getLocalHost().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			this.matchId = manager.add(0, jugadorFicticio.getId());
			this.match = (Sudoku) Manager.get().findGameById(0).findMatchById(matchId, jugadorFicticio.getId());
			this.lastBoard = this.match.getBoard();
			this.solvedBoard = this.match.getSolvedBoard();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return match;
	}

	private String createBoardToSend(String board, String solvedBoard) {
		String boardToSend = StringUtils.EMPTY;
		int correctMoves = 0;
		for (int i = 0; i < board.length(); i++) {
			if (correctMoves < 1 && Math.random() < 0.5 && board.charAt(i) != solvedBoard.charAt(i)) {
				boardToSend += solvedBoard.charAt(i);
				correctMoves += 1;
			}
			else{
				boardToSend += board.charAt(i);
			}
		}
		return boardToSend;
	}

	private void realizarMovimiento() {
		try {
			this.lastBoard = createBoardToSend(this.lastBoard, solvedBoard);
			SudokuBoardMessage jsoM = new SudokuBoardMessage(this.lastBoard, String.valueOf(jugadorFicticio.getId()),
					String.valueOf(jugadorFicticio.getId()), matchId);
			match.move(jugadorFicticio, jsoM.toJSONObject());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		identificarse();
		entrarEnPartida();
		while (true) {
			realizarMovimiento();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
