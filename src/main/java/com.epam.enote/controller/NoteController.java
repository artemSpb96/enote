package com.epam.enote.controller;

import com.epam.enote.model.Note;
import com.epam.enote.service.NoteService;
import com.epam.enote.service.NotebookService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/users/{userId}/notebooks/{notebookId}/notes")
public class NoteController {

    @Autowired
    private NotebookService notebookService;

    @Autowired
    private NoteService noteService;

    @GetMapping(path = "/{noteId}", produces = "application/json")
    public ResponseEntity<Note> getNote(@PathVariable Long userId,
                                              @PathVariable Long notebookId,
                                              @PathVariable Long noteId) {
        Note note = noteService.getNote(userId, notebookId, noteId);

        return new ResponseEntity<>(note, HttpStatus.OK);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Note>> getNote(@PathVariable Long userId,
                                        @PathVariable Long notebookId) {
        List<Note> notes = noteService.getNotes(userId, notebookId);

        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @PostMapping(path = "/{noteId}", consumes = "application/json")
    public ResponseEntity<Long> addNote(@PathVariable Long userId,
                                        @PathVariable Long notebookId,
                                        @RequestBody Note note) {
        Long noteId = notebookService.addNote(userId, notebookId, note);

        return new ResponseEntity<>(noteId, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{noteId}", consumes = "application/json")
    public ResponseEntity<Void> updateNote(@PathVariable Long userId,
                                           @PathVariable Long notebookId,
                                           @PathVariable Long noteId,
                                           @RequestBody Note note) {
        noteService.updateNote(userId, notebookId, noteId, note);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long userId,
                                           @PathVariable Long notebookId,
                                           @PathVariable Long noteId) {
        notebookService.removeNote(userId, notebookId, noteId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
