package edu.uclm.esi.common.jsonMessages;

import org.json.JSONException;
import org.json.JSONObject;

public class SudokuBoardMessage extends JSONMessage{
    private String board;
    private String user1;
    private String user2;
    private int idMatch;

    public SudokuBoardMessage (String board, String user1, String user2, int idMatch){
        super(false);
        this.board=board;
        this.user1=user1;
        this.user2=user2;
        this.idMatch=idMatch;
    }

    public SudokuBoardMessage (JSONObject jso) throws JSONException {
        this(jso.getString("board"), jso.getString("user1"), jso.getString("user2"),jso.getInt("idMatch"));
    }

    public int getIdMatch(){
        return idMatch;
    }

    public String getBoard(){
        return board;
    }

    public String getUser1(){
        return user1;
    }

    public String getUser2(){
        return user2;
    }
}
