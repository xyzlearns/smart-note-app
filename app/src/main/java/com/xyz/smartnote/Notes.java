package com.xyz.smartnote;

public class Notes {
    private String note;
    private String title;
    private int id;

    public Notes(String title,String note) {
        this.note = note;
        this.title = title;
    }

    public Notes(String title,String note,int id) {
        this.note = note;
        this.title = title;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
