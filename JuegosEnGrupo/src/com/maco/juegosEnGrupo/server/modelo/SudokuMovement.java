package com.maco.juegosEnGrupo.server.modelo;

public class SudokuMovement {
	private int row;
	private int col;
	private int value;

	public SudokuMovement(int row, int col, int value) {
		this.row = row;
		this.col = col;
		this.value = value;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public int getValue() {
		return value;
	}

}
