package edu.uclm.esi.common.server.actions;

import org.json.JSONObject;

import com.opensymphony.xwork2.ActionContext;

import edu.uclm.esi.common.server.domain.Manager;
import edu.uclm.esi.common.server.domain.Ranking;

@SuppressWarnings("serial")
public class ShowRanking extends JSONAction {
	private JSONObject jsoRanking;
	
	@Override
	protected String postExecute() {
		try {
			Manager manager=Manager.get();
			Ranking ranking = manager.getRanking();
			jsoRanking=ranking.toJSON();
			return SUCCESS;
		} catch (Exception e){
			ActionContext.getContext().getSession().put("exception", e);
			this.exception=e;
			return ERROR;
		}
	}

	@Override
	public void setCommand(String cmd) {
		
	}

	@Override
	public String getResultado() {
		return jsoRanking.toString();
	}
	
}
