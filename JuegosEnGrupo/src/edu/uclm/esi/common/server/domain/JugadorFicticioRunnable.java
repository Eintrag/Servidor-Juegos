package edu.uclm.esi.common.server.domain;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;

import com.maco.juegosEnGrupo.server.dominio.Match;
import com.maco.juegosEnGrupo.server.dominio.Sudoku;
import com.maco.juegosEnGrupo.server.modelo.DatosJugadorFicticio;
import com.maco.juegosEnGrupo.server.modelo.SudokuMovement;
import com.maco.juegosEnGrupo.server.modelo.SudokuUtils;

import edu.uclm.esi.common.jsonMessages.SudokuBoardMessage;
import edu.uclm.esi.common.jsonMessages.SudokuMovementMessage;

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

	private SudokuMovement createBoardToSend(String board, String solvedBoard) {
		SudokuMovement movement = null;
		String newBoard = "";
		while (movement == null) {
			for (int i = 0; i < board.length() && movement == null; i++) {
				if (Math.random() > 0.8 && board.charAt(i) != solvedBoard.charAt(i)) {
					movement = new SudokuMovement(SudokuUtils.calculateRow(i), SudokuUtils.calculateCol(i),
							Character.getNumericValue(solvedBoard.charAt(i)));
					newBoard += solvedBoard.charAt(i);
				} else {
					newBoard += board.charAt(i);
				}
			}
		}
		return movement;
	}

	private void realizarMovimiento() {
		try {
			SudokuMovement movimiento = createBoardToSend(lastBoard, solvedBoard);
			if (movimiento != null) {
				SudokuMovementMessage jmm = new SudokuMovementMessage(movimiento.getRow(), movimiento.getCol(),
						movimiento.getValue(), jugadorFicticio.getId(), matchId);
				match.move(jugadorFicticio, jmm.toJSONObject());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		identificarse();
		try {
			Thread.sleep(DatosJugadorFicticio.getTimeWaitToJoin());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		entrarEnPartida();
		if (this.match.isComplete()) {
			while (true) {
				if(false){
					realizarMovimiento();
				}
				try {
					Thread.sleep(DatosJugadorFicticio.getTimeBetweenMoves());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
