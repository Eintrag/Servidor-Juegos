package edu.uclm.esi.common.server.actions;

import java.util.ConcurrentModificationException;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.uclm.esi.common.jsonMessages.ErrorMessage;
import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.MessageList;
import edu.uclm.esi.common.server.domain.Manager;
import edu.uclm.esi.common.server.domain.User;

public class GetMensajes extends JSONAction {

	private String email;
	private Vector<JSONMessage> mensajes;

	@Override
	protected String postExecute() {
		Manager manager = Manager.get();
		User user = manager.findUserByEmail(this.email);
		this.mensajes = user.getMensajesPendientes();
		return SUCCESS;
	}

	@Override
	public void setCommand(String cmd) {
		try {
			JSONObject jso = new JSONObject(cmd);
			this.email = jso.get("email").toString();
		} catch (Exception e) {
			this.exception = e;
		}
	}

	@Override
	public String getResultado() {
		if (this.exception != null) {
			JSONMessage resultado = new ErrorMessage(this.exception.getMessage());
			return resultado.toString();
		} else {
			MessageList ml = new MessageList();
			try {
				for (JSONMessage mensaje : this.mensajes) {
					ml.add(mensaje.toJSONObject());
				}
				mensajes.clear();
			} catch (ConcurrentModificationException e) {}
			String s = ml.toString();
			return s;
		}
	}

}
