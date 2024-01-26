package com.mtg.app.controller;

import com.mtg.app.dtos.NoteDto;
import com.mtg.app.dtos.request.NoteUpdateDto;
import com.mtg.app.dtos.response.NoteResponseDto;
import com.mtg.app.services.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/notes")
@CrossOrigin(origins = "http://localhost:3000")
public class NoteController {

    private final NoteService noteService;

    @GetMapping
    public ResponseEntity<List<NoteDto>> getNotesForMeeting(@PathVariable UUID noteId) {
        List<NoteDto> notes = noteService.getNotesForMeeting(noteId);
        return ResponseEntity.ok(notes);

    }

    @PostMapping
    public ResponseEntity<NoteResponseDto> addNoteToMeeting(@RequestBody NoteDto noteDTO) {
        NoteDto createdNote = noteService.addNoteToMeeting(noteDTO);

        NoteResponseDto response = new NoteResponseDto();
        response.setNoteDto(createdNote);
        response.setMessage("Note created successfully.");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{noteId}")
    public ResponseEntity<NoteResponseDto> updateNote(@PathVariable UUID noteId, @RequestBody NoteUpdateDto updateNote) {
        NoteDto updatedNote = noteService.updateNote(noteId, updateNote);

        NoteResponseDto response = new NoteResponseDto();
        response.setNoteDto(updatedNote);
        response.setMessage("Note updated successfully.");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<String> deleteNote(@PathVariable UUID noteId) {
        noteService.deleteNote(noteId);
        return ResponseEntity.ok("Note deleted successfully.");
    }
}
