package edu.uclm.esi.common.jsonMessages;

import org.json.JSONException;
import org.json.JSONObject;

public class JoinGameMessage extends JSONMessage{
    private int idUser;
    private int idGame;

    public JoinGameMessage(int idUser, int idGame){
        super(true);
        this.idUser=idUser;
        this.idGame=idGame;
    }

    public JoinGameMessage(JSONObject jso) throws JSONException{
        this(Integer.parseInt(jso.get("idUser").toString()),Integer.parseInt(jso.get("idGame").toString()));
    }
    public int getIdUser (){
        return idUser;
    }

    public int getIdGame(){
        return idGame;
    }
}
