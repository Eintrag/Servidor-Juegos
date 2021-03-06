package com.maco.juegosEnGrupo.server.actions;

import org.json.JSONException;
import org.json.JSONObject;

import com.maco.juegosEnGrupo.server.dominio.Game;
import com.maco.juegosEnGrupo.server.dominio.Match;
import com.opensymphony.xwork2.ActionContext;

import edu.uclm.esi.common.jsonMessages.ErrorMessage;
import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.OKMessage;
import edu.uclm.esi.common.server.actions.JSONAction;
import edu.uclm.esi.common.server.domain.Manager;
import edu.uclm.esi.common.server.domain.User;

@SuppressWarnings("serial")
public class SendMovement extends JSONAction {
	private int idUser;
	private int idGame;
	private int idMatch;
	private String board;
	private JSONObject jsoMovement;
	private int ID_GAME_SUDOKU = 0;
	@Override
	public String postExecute() {
		try {
			Manager manager=Manager.get();
			User user=manager.findUserById(this.idUser);
			if (user==null)
				throw new Exception("Usuario no autenticado");
			Game g=manager.findGameById(ID_GAME_SUDOKU);
			Match match=g.findMatchById(idMatch, idUser);
			match.move(user, this.jsoMovement);
			return SUCCESS;
		} catch (Exception e) {
			this.exception=e;
			ActionContext.getContext().getSession().put("exception", e);
			return ERROR;
		}
	}

	@Override
	public String getResultado() {
		JSONMessage jso;
		if (this.exception!=null)
			jso=new ErrorMessage(this.exception.getMessage());
		else
			jso=new OKMessage();
		return jso.toJSONObject().toString();
	}

	@Override
	public void setCommand(String cmd) {
		try {
			this.jsoMovement = new JSONObject(cmd);
			this.idGame = ID_GAME_SUDOKU;
			this.idMatch=jsoMovement.getInt("idMatch");
			this.idUser = jsoMovement.getInt("idUser");
		} catch (JSONException e) {
			this.exception=e;
		}
	}
	
	public void setIdUser(int idUser) {
		this.idUser=idUser;
	}
	
	public void setIdGame(int idGame) {
		this.idGame = idGame;
	}
	
	public void setIdMatch(int idMatch) {
		this.idMatch=idMatch;
	}
}