package edu.uclm.esi.common.server.actions;

import com.opensymphony.xwork2.ActionContext;

import edu.uclm.esi.common.server.domain.Manager;
import edu.uclm.esi.common.server.domain.Ranking;

public class ShowRanking extends JSONAction {
	private Ranking ranking;
	
	@Override
	protected String postExecute() {
		try {
			Manager manager=Manager.get();
			ranking = manager.getRanking();
			ranking.toJSON();
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
		// TODO Auto-generated method stub
		return null;
	}
	
}
