package com.maco.juegosEnGrupo.server.dominio;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.maco.juegosEnGrupo.server.modelo.SudokuBoardsEnum;

import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.OKMessage;
import edu.uclm.esi.common.jsonMessages.SudokuBoardMessage;
import edu.uclm.esi.common.jsonMessages.SudokuWaitingMessage;
import edu.uclm.esi.common.server.domain.Manager;
import edu.uclm.esi.common.server.domain.User;
import sun.java2d.loops.ScaledBlit;

public class Sudoku extends Match {
	private String board;
	private final int BOARDSIZE;
	public Sudoku(Game game) {
		super(game);
		// TODO construir el tablero mejor
		board = SudokuBoardsEnum.getAny().toString();
		BOARDSIZE = 81;
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

	public void postMove(User user, JSONObject jsoMovement) throws Exception {
		SudokuBoardMessage boardMessage = new SudokuBoardMessage(jsoMovement);
		String boardReceived = boardMessage.getBoard();
		String boardWithHiddenCells = "";
		for (int i = 0; i < BOARDSIZE; i++){
			if (board.charAt(i)!=boardReceived.charAt(i)){
				boardWithHiddenCells += "*";
			}
			else {
				boardWithHiddenCells += board.charAt(i);
			}
		}
		JSONMessage newBoard = new SudokuBoardMessage(boardWithHiddenCells, this.players.get(0).toString(),
				this.players.get(1).toString(), boardMessage.getIdMatch());
		for (User player : this.players){
			if (player.getId() != jsoMovement.getInt("user1")){
				player.addMensajePendiente(newBoard);
			}
		}
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
