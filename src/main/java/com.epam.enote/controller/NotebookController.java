package com.epam.enote.controller;

import com.epam.enote.model.Notebook;
import com.epam.enote.service.NotebookService;
import com.epam.enote.service.UserService;
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
@RequestMapping("/users/{userId}/notebooks")
public class NotebookController {

    @Autowired
    private NotebookService notebookService;

    @Autowired
    private UserService userService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Notebook>> getNotebooks(@PathVariable Long userId) {
        List<Notebook> notebooks = notebookService.getNotebooks(userId);

        return new ResponseEntity<>(notebooks, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Long> addNotebook(@PathVariable Long userId,
                                            @RequestBody Notebook notebook) {
        return new ResponseEntity<>(userService.addNotebook(userId, notebook), HttpStatus.CREATED);
    }

    @GetMapping(path = "/{notebookId}", produces = "application/json")
    public ResponseEntity<Notebook> getNotebook(@PathVariable Long userId,
                                                @PathVariable Long notebookId) {
        return new ResponseEntity<>(notebookService.getNotebook(userId, notebookId), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{notebookId}", consumes = "application/json")
    public ResponseEntity<Void> deleteNotebook(@PathVariable Long userId,
                                               @PathVariable Long notebookId) {
        userService.removeNotebook(userId, notebookId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
