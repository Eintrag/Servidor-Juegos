package com.maco.juegosEnGrupo.server.dominio;

import java.io.IOException;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.maco.juegosEnGrupo.server.modelo.SudokuBoardsEnum;

import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.LostGameMessage;
import edu.uclm.esi.common.jsonMessages.OKMessage;
import edu.uclm.esi.common.jsonMessages.SudokuBoardMessage;
import edu.uclm.esi.common.jsonMessages.SudokuWaitingMessage;
import edu.uclm.esi.common.jsonMessages.WonGameMessage;
import edu.uclm.esi.common.server.domain.Manager;
import edu.uclm.esi.common.server.domain.User;
import sun.java2d.loops.ScaledBlit;

public class Sudoku extends Match {
	private String board;
	private String solvedBoard;
	private final int BOARDSIZE;
	private int idLatestUpdateUser;
	private int offline = 0;
	private long startTime;

	public Sudoku(Game game) {
		super(game);
		// TODO construir el tablero mejor
		SudokuBoardsEnum currentBoardEnum = SudokuBoardsEnum.getAny();
		board = currentBoardEnum.getBoard();
		solvedBoard = currentBoardEnum.getSolvedBoard();
		BOARDSIZE = board.length();
		startTime = System.currentTimeMillis();
	}

	String getBoard() {
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

	private void concludeGame(int winner, int seconds) {
		for (User player : this.players) {
			if (player.getId() != winner) {
				player.addMensajePendiente(new LostGameMessage());
			} else {
				player.addMensajePendiente(new WonGameMessage());
			}
		}
		Manager manager=Manager.get();
		User userWinner=manager.findUserById(winner);
		User userLoser;
		if(players.get(0).equals(userWinner))
			userLoser=players.get(1);
		else
			userLoser=players.get(0);
		try {
			addPartida(userWinner.getEmail(), userLoser.getEmail(),seconds);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void postMove(User user, JSONObject jsoMovement) throws Exception {
		if(!(idLatestUpdateUser==user.getId())){ //Control de posible pérdida de la conexión.
			offline = 0;
			idLatestUpdateUser = user.getId();
		}else{
			offline++;
		}
		SudokuBoardMessage boardMessage = new SudokuBoardMessage(jsoMovement);
		String boardReceived = boardMessage.getBoard();
		if (isSudokuComplete(boardReceived) || offline >= 3) {
			concludeGame(jsoMovement.getInt("user1"), (int)((System.currentTimeMillis()-startTime)/1000));
			//restar el tiempo actual (System.currentTimeMillis()) con startTime y meterlo en el ranking junto con el emali del User ganador.
		} else {
			JSONMessage newBoard = new SudokuBoardMessage(ofuscateBoardForOpponent(boardReceived),
					this.players.get(0).toString(), this.players.get(1).toString(), boardMessage.getIdMatch());
			for (User player : this.players) {
				if (player.getId() != jsoMovement.getInt("user1")) {
					player.addMensajePendiente(newBoard);
				}
			}
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

	@Override
	protected void postAddUser(User user) {
		if (this.players.size() == 2) {
			JSONMessage matchReady = new SudokuBoardMessage(this.getBoard(), this.players.get(0).toString(),
					this.players.get(1).toString(), this.hashCode());
			try {
				for (User player : this.players) {
					player.addMensajePendiente(matchReady);
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
