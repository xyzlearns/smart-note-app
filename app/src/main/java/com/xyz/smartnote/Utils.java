package com.xyz.smartnote;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {

private static final String NOTES_KEY = "all_notes";
private static final String ID_KEY = "notes_id";
    private SharedPreferences sharedPreferences;


//    private static ArrayList<Notes> notes;

    // Instance creation : singleton pattern
    private static Utils instance;
    public static Utils getInstance(Context context){

        if(null == instance){
            instance = new Utils(context);
            return instance;
        }
        return instance;
    }

    //Constructor
    public Utils(Context context) {
        sharedPreferences = context.getSharedPreferences("alternate_db",context.MODE_PRIVATE);
        if(null == getNotes()){
            initData();
        }

    }

    private void initData(){
        ArrayList<Notes> notes = new ArrayList<>();
        Notes note = new Notes("Sample Heading","sample note",1);
        notes.add(note);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString(NOTES_KEY,gson.toJson(notes));
        editor.putInt(ID_KEY,1);
        editor.commit();
    }

    public ArrayList<Notes> getNotes() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Notes>>(){}.getType();
        ArrayList<Notes> notes = gson.fromJson(sharedPreferences.getString(NOTES_KEY,null),type);
        return notes;
    }

    public void setNotes(Notes note) {
        ArrayList<Notes> notes = getNotes();
        int id = sharedPreferences.getInt(ID_KEY,-1) + 1;
        note.setId(id);
        notes.add(note);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(NOTES_KEY);
        editor.remove(ID_KEY);
        Gson gson = new Gson();
        editor.putString(NOTES_KEY,gson.toJson(notes));
        editor.putInt(ID_KEY,id);
        editor.commit();
    }

    public void removeNotes(Notes note){
        ArrayList<Notes> notes = getNotes();
        for(Notes n: notes){
            if(n.getId()==note.getId()){
                notes.remove(n);
                break;
            }
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(NOTES_KEY);
        Gson gson = new Gson();
        editor.putString(NOTES_KEY,gson.toJson(notes));
        editor.commit();
    }
}
