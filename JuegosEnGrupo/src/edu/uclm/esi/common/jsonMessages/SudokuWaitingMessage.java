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
        this(jso.getString("additionalData"));
    }

    public String getAdditionalData(){
        return additionalData;
    }
}
