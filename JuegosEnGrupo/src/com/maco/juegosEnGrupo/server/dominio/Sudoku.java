package com.maco.juegosEnGrupo.server.dominio;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.maco.juegosEnGrupo.server.modelo.DatosJugadorFicticio;
import com.maco.juegosEnGrupo.server.modelo.SudokuBoardsEnum;

import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.OKMessage;
import edu.uclm.esi.common.jsonMessages.SudokuBoardMessage;
import edu.uclm.esi.common.jsonMessages.SudokuMovementAnnouncementMessage;
import edu.uclm.esi.common.jsonMessages.SudokuMovementMessage;
import edu.uclm.esi.common.jsonMessages.SudokuWaitingMessage;
import edu.uclm.esi.common.jsonMessages.SudokuWinnerMessage;
import edu.uclm.esi.common.server.domain.JugadorFicticioRunnable;
import edu.uclm.esi.common.server.domain.Manager;
import edu.uclm.esi.common.server.domain.User;
import sun.java2d.loops.ScaledBlit;

public class Sudoku extends Match {
	private String board;
	private String solvedBoard;
	private final int BOARDSIZE;
	private long startTime;
	private boolean isGameEnded = false;
	private int winner;
	private Map<Integer, String> userBoards = new HashMap<Integer, String>();

	public Sudoku(Game game) {
		super(game);
		SudokuBoardsEnum currentBoardEnum = SudokuBoardsEnum.getAny();
		board = currentBoardEnum.getBoard();
		solvedBoard = currentBoardEnum.getSolvedBoard();
		BOARDSIZE = board.length();
		startTime = System.currentTimeMillis();
	}

	public String getSolvedBoard() {
		return solvedBoard;
	}

	public String getBoard() {
		return board;
	}

	@Override
	public String toString() {
		String r = "";
		r += this.getBoard();
		r += "#" + this.players.get(0).getEmail() + "#";
		if (this.players.size() == 2) {
			r += this.players.get(1).getEmail() + "#";
		}
		return r;
	}

	private boolean isSudokuComplete(String boardReceived) {
		return boardReceived.equals(solvedBoard);
	}
	public void concludeGame(User winner){
		int finishTime = (int) ((System.currentTimeMillis() - startTime) / 1000);
		concludeGame(winner.getId(), finishTime);
		sendResults(winner.getId(), finishTime);
	}
	private void concludeGame(int winner, int seconds) {
		Manager manager = Manager.get();
		User userWinner = manager.findUserById(winner);
		User userLoser;
		if (players.get(0).equals(userWinner))
			userLoser = players.get(1);
		else
			userLoser = players.get(0);
		try {
			addPartida(userWinner.getEmail(), userLoser.getEmail(), seconds);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		isGameEnded = true;
	}

	public void postMove(User user, JSONObject jsoMovement) throws Exception {
		int finishTime = -1;
		if (!isGameEnded) {
			SudokuMovementMessage smm = new SudokuMovementMessage(jsoMovement);
			int userId = smm.getUser();
			setLastBoard(userId, updateBoard(userId, smm.getRow(), smm.getCol(), smm.getValue()));

			JSONMessage smam = new SudokuMovementAnnouncementMessage(smm.getRow(), smm.getCol(), -1, smm.getIdMatch());
			for (User player : this.players) {
				if (player.getId() != userId) {
					player.addMensajePendiente(smam);
				}
			}
			if (isSudokuComplete(getLastBoard(userId))) {
				this.winner = jsoMovement.getInt("idUser");
				finishTime = (int) ((System.currentTimeMillis() - startTime) / 1000);
				concludeGame(winner, finishTime);
				sendResults(winner, finishTime);
			}
		}
//		} else {
//			sendResults(winner, finishTime);
//		}
	}

	private void sendResults(int winner, int seconds) {
		for (User player : this.players) {
			player.addMensajePendiente(new SudokuWinnerMessage(String.valueOf(winner), seconds, this.hashCode()));
		}
	}

	private String ofuscateBoardForOpponent(String boardReceived) {
		String boardWithHiddenCells = "";
		for (int i = 0; i < BOARDSIZE; i++) {
			if (board.charAt(i) != boardReceived.charAt(i)) {
				boardWithHiddenCells += "*";
			} else {
				boardWithHiddenCells += board.charAt(i);
			}
		}
		return boardWithHiddenCells;
	}

	protected void updateBoard(int row, int col, JSONMessage result) {
	}

	protected String updateBoard(int user, int row, int col, int value) {
		String lastBoard = getLastBoard(user);
		String newBoard = StringUtils.EMPTY;
		for (int i = 0; i < BOARDSIZE; i++) {
			if (i / 9 == row && i % 9 == col) {
				newBoard += String.valueOf(value);
			} else
				newBoard += lastBoard.charAt(i);
		}
		return newBoard;
	}

	private String getLastBoard(int userId) {
		return this.userBoards.get(userId);
	}

	private void setLastBoard(int userId, String board) {
		this.userBoards.replace(userId, board);
	}

	@Override
	protected void postAddUser(User user) {
		if (this.players.size() == 2) {
			JSONMessage matchReady = new SudokuBoardMessage(this.getBoard(), this.players.get(0).toString(),
					this.players.get(1).toString(), this.hashCode());
			try {
				for (User player : this.players) {
					player.addMensajePendiente(matchReady);
					this.userBoards.put(player.getId(), this.getBoard());
					//Se crea un vigilante por jugador.
					Manager.get().vigilaUser(player, this);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			JSONMessage jsm = new SudokuWaitingMessage("Waiting for one more player to start sudoku");

			try {
				for (User player : this.players) {
					player.addMensajePendiente(jsm);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	protected boolean isTheTurnOf(User user) {
		// TODO Auto-generated method stub
		return true;
	}

}
