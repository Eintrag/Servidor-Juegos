package com.maco.juegosEnGrupo.server.modelo;

public class DatosJugadorFicticio {
	private static String email = "jugadorFicticio@maquina.com";
	private static String password = "1234";
	private static long timeWaitToJoin = 500;
	private static long timeBetweenMoves = 5000;

	public static String getEmail() {
		return email;
	}

	public static String getPassword() {
		return password;
	}

	public static long getTimeWaitToJoin() {
		return timeWaitToJoin;
	}

	public static long getTimeBetweenMoves() {
		return timeBetweenMoves;
	}

}
