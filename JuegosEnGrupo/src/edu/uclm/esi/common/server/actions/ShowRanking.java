package edu.uclm.esi.common.server.actions;

import org.json.JSONArray;
import org.json.JSONException;

import com.opensymphony.xwork2.ActionContext;

import edu.uclm.esi.common.jsonMessages.ErrorMessage;
import edu.uclm.esi.common.jsonMessages.JSONMessage;
import edu.uclm.esi.common.jsonMessages.RankingMessage;
import edu.uclm.esi.common.server.domain.Manager;
import edu.uclm.esi.common.server.domain.Ranking;
import edu.uclm.esi.common.server.domain.IRankingEntry;

@SuppressWarnings("serial")
public class ShowRanking extends JSONAction {
	Ranking ranking;
	
	@Override
	protected String postExecute() {
		try {
			Manager manager=Manager.get();
			this.ranking = manager.getRanking();
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
		JSONMessage result;
		if (this.exception!=null) {
			result=new ErrorMessage(this.exception.getMessage());
		} else {
			JSONArray rankingEntries=new JSONArray();			
			for (IRankingEntry re : this.ranking.getEntries()) {
				try {
					rankingEntries.put(re.toJSON());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			result=new RankingMessage(rankingEntries);
		}
		String s=result.toString();
		return s;
	}
}
