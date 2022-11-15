package com.example.notes;

public class NotesModel {
    public String notes;
    public NotesModel(){

    }

    public NotesModel(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
