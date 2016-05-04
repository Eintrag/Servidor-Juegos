package com.maco.juegosEnGrupo.server.modelo;

public class SudokuUtils {
	public static int calculateRow(int value){
		return value / 9;
	}
	public static int calculateCol(int value){
		return value % 9;
	}
}
