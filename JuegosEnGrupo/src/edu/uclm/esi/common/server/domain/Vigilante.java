package edu.uclm.esi.common.server.domain;

import com.maco.juegosEnGrupo.server.dominio.Match;

public class Vigilante implements Runnable{
	
	private User user;
	private Match match;
	
	public Vigilante (User user, Match match){
		this.user = user;
		this.match = match;
	}
	
	@Override
	public void run() {
		boolean desconectado = false;
		user.updateLastUpdate();
		while(!desconectado){
			if(((int)(System.currentTimeMillis() - user.getlastUpdate())) > 15000 ){ //Si se detecta que un jugador lleva más de 15 segundos sin recibir actualizaciones del rival.
				Manager manager = Manager.get();
				manager.concludeGame(user, match);
				desconectado = true;
			}
			try {
				Thread.sleep(15000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
