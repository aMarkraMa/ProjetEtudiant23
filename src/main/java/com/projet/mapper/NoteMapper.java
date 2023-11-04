package com.projet.mapper;

import com.projet.entity.Etudiant;
import com.projet.entity.Note;

import java.util.ArrayList;

public interface NoteMapper {
    int insertNote(Note note);
    int deleteNote(Note note);
    int updateNote(Note note);
    ArrayList<Note> selectNote(Note note);
}