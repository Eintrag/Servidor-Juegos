package edu.uclm.esi.common.jsonMessages;

import org.json.JSONException;
import org.json.JSONObject;

public class SudokuWaitingMessage extends JSONMessage{
    @JSONable
    private String additionalData;

    public SudokuWaitingMessage(String additionalData){
        super(false);
        this.additionalData=additionalData;
    }

    public SudokuWaitingMessage(JSONObject jso) throws JSONException {
        super(false);
        if (jso.has("additionalData")) {
            this.additionalData=jso.getString("additionalData");
        }
    }

    public String getAdditionalData(){
        return additionalData;
    }
}
