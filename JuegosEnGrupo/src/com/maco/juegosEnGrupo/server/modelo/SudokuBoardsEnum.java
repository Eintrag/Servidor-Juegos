package com.maco.juegosEnGrupo.server.modelo;

import java.util.Random;

public enum SudokuBoardsEnum {
	BOARD1(
	"53  7    "+
	"6  195   "+
	" 98    6 "+
	"8   6   3"+
	"4  8 3  1"+
	"7   2   6"+
	" 6    28 "+
	"   418  5"+
	"    8  79"),
	BOARD2(
			"  1 4    "+
			"39       "+
			"8  713 56"+
			"16 2  58 "+
			"72 3 5 64"+
			" 84  1 93"+
			"45 927  8"+
			"       25"+
			"    5 6  "),
	BOARD3(
			"3 4 86   "+
			" 6      1"+
			" 283     "+
			" 39 6 7 4"+
			"6  432  5"+
			"5 1 7 36 "+
			"     342 "+
			"8      9 "+
			"   81 6 3");
	private String board;
	private SudokuBoardsEnum(String board){
		this.board = board;
	}
	public String toString(){
		return board;
	}
	public static SudokuBoardsEnum getAny(){
		Random random = new Random();
		return SudokuBoardsEnum.values()[random.nextInt(SudokuBoardsEnum.values().length)];
	}
}
