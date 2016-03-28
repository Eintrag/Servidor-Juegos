package com.maco.juegosEnGrupo.server.dominio;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.OKMessage;
import edu.uclm.esi.common.jsonMessages.SudokuBoardMessage;
import edu.uclm.esi.common.jsonMessages.SudokuWaitingMessage;
import edu.uclm.esi.common.server.domain.Manager;
import edu.uclm.esi.common.server.domain.User;

public class Sudoku extends Match {
	final private char blank = '\0';
	public static int TRES_EN_RAYA = 1;
	private char[][] squares;

	public Sudoku(Game game) {
		super(game);
		squares = new char[9][9];
		for (int row = 0; row < 9; row++)
			for (int col = 0; col < 9; col++)
				squares[row][col] = blank;
		// TODO construir el tablero mejor
		squares[2][3] = '4';
		squares[3][5] = '6';
		squares[7][7] = '9';
	}

	String getTablero() {
		String r = "";
		for (int row = 0; row < 9; row++)
			for (int col = 0; col < 9; col++)
				r += this.squares[row][col];
		return r;
	}

	@Override
	public String toString() {
		String r = "";
		r += this.getTablero();
		r += "#" + this.players.get(0).getEmail() + "#";
		if (this.players.size() == 2) {
			r += this.players.get(1).getEmail() + "#";
		}
		return r;
	}

	// @Override
	// public void postMove(User user, JSONObject jsoMovement) throws Exception

	// @Override
	// protected void updateBoard(int row, int col, JSONMessage result)


	@Override
	protected void postAddUser(User user) {
		if (this.players.size() == 2) {
			JSONMessage matchReady = new SudokuBoardMessage(this.getTablero(), this.players.get(0).toString(),
					this.players.get(1).toString(), 2);
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
	protected void postMove(User user, JSONObject jsoMovement) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void updateBoard(int row, int col, JSONMessage result) throws JSONException, IOException {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean isTheTurnOf(User user) {
		// TODO Auto-generated method stub
		return false;
	}

}
