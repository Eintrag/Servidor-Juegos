package com.maco.juegosEnGrupo.server.modelo;

import java.util.Random;

public enum SudokuBoardsEnum {
	//
	// TESTINGBOARD(
	// " 8145629" + //
	// "149862753" + //
	// "526397148" + //
	// "835921476" + //
	// "261473895" + //
	// "794658312" + //
	// "983514267" + //
	// "617289534" + //
	// "452736981"//
	// , //
	// "378145629" + //
	// "149862753" + //
	// "526397148" + //
	// "835921476" + //
	// "261473895" + //
	// "794658312" + //
	// "983514267" + //
	// "617289534" + //
	// "452736981"//
	// ); //

	BOARD1(" 781   2 " + //
			"1   62  3" + //
			"5   9    " + //
			"8     4 6" + //
			" 61 7  9 " + //
			" 9    3  " + //
			"   5 42 7" + //
			"6   8  3 " + //
			" 5 7  9  "//
			, //
			"378145629" + //
					"149862753" + //
					"526397148" + //
					"835921476" + //
					"261473895" + //
					"794658312" + //
					"983514267" + //
					"617289534" + //
					"452736981"//
	) //
	// BOARD2(" 781 2 " + //
	// "1 62 3" + //
	// "5 9 " + //
	// "8 4 6" + //
	// " 61 7 9 " + //
	// " 9 3 " + //
	// " 5 42 7" + //
	// "6 8 3 " + //
	// " 5 7 9 "//
	// , //
	// "378145629" + //
	// "149862753" + //
	// "526397148" + //
	// "835921476" + //
	// "261473895" + //
	// "794658312" + //
	// "983514267" + //
	// "617289534" + //
	// "452736981")//
	; // */

	private String board;
	private String solvedBoard;

	private SudokuBoardsEnum(String board, String solvedBoard) {
		this.board = board;
		this.solvedBoard = solvedBoard;
	}

	public String getBoard() {
		return board;
	}

	public String getSolvedBoard() {
		return solvedBoard;
	}

	public static SudokuBoardsEnum getAny() {
		Random random = new Random();
		return SudokuBoardsEnum.values()[random.nextInt(SudokuBoardsEnum.values().length)];
	}
}
