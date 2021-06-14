package com.example.easynotes.controller;

import java.util.List;

import javax.validation.Valid;

import com.example.easynotes.model.Note;
import com.example.easynotes.repository.NoteRepository;
import com.example.easynotes.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class NoteController {
    
    @Autowired
    NoteRepository noteRepository;

    @GetMapping("/notes")
    public List<Note> getAllNote(){
        return noteRepository.findAll();
    }

    @PostMapping("/notes")
    public Note creatreNote(@Valid @RequestBody Note note){
        return noteRepository.save(note);
    }

    @GetMapping("/notes/{id}")
    public Note getNoteById(@PathVariable(value = "id") Long noteId){
        return noteRepository.findById(noteId)
        .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
    }

    @PutMapping("/notes/{id}")
    public Note updatNote(@PathVariable(value = "id") Long noteId, @Valid @RequestBody Note noteDetail){

        Note note = noteRepository.findById(noteId)
            .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
        
        note.setTitle(noteDetail.getTitle());
        note.setContent(noteDetail.getContent());

        Note updatNote = noteRepository.save(note);
        return updatNote;
    }

    @DeleteMapping("notes/{id}")
    public ResponseEntity<?> deleNote(@PathVariable(value = "id") Long noteId){

        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
        
        noteRepository.delete(note);
        
        return ResponseEntity.ok().build();
    }

}
