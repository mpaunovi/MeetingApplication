package com.mtg.app.services;

import com.mtg.app.dtos.NoteDto;
import com.mtg.app.dtos.request.NoteUpdateDto;

import java.util.List;
import java.util.UUID;

public interface NoteService {

    List<NoteDto> getNotesForMeeting(UUID noteId);

    public NoteDto addNoteToMeeting(NoteDto noteDTO);

    public NoteDto updateNote(UUID noteId, NoteUpdateDto noteDTO);

    public void deleteNote(UUID noteId);
}
