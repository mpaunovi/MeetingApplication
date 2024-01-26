package com.mtg.app.services.impl;

import com.mtg.app.dtos.NoteDto;
import com.mtg.app.dtos.request.NoteUpdateDto;
import com.mtg.app.exception.ResourceNotFoundException;
import com.mtg.app.mappers.NoteMapper;
import com.mtg.app.models.Meeting;
import com.mtg.app.models.Note;
import com.mtg.app.models.User;
import com.mtg.app.repository.MeetingRepository;
import com.mtg.app.repository.NoteRepository;
import com.mtg.app.repository.UserRepository;
import com.mtg.app.services.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final MeetingRepository meetingRepository;
    private final UserRepository userRepository;

    @Override
    public List<NoteDto> getNotesForMeeting(UUID noteId) {
        List<Note> notes = noteRepository.findByMeetingId(noteId);
        return notes.stream().map(NoteMapper::mapToNoteDto).collect(Collectors.toList());
    }

    @Override
    public NoteDto addNoteToMeeting(NoteDto noteDto) {
        Meeting meeting = meetingRepository.findById(noteDto.getMeetingId())
                .orElseThrow(() -> new ResourceNotFoundException("Meeting not found with ID: " + noteDto.getMeetingId()));

        User user = userRepository.findById(noteDto.getContributorId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + noteDto.getContributorId()));

        Note note = new Note();
        note.setMeeting(meeting);
        note.setContributor(user);
        note.setTitle(noteDto.getTitle());
        note.setContent(noteDto.getContent());

        Note savedNote = noteRepository.save(note);

        meeting.getNotes().add(savedNote);
        meetingRepository.save(meeting);

        user.getContributedNotes().add(savedNote);
        userRepository.save(user);

        return NoteMapper.mapToNoteDto(savedNote);
    }

    @Override
    public NoteDto updateNote(UUID noteId, NoteUpdateDto updateNote) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with ID: " + noteId));

        note.setTitle(updateNote.getTitle());
        note.setContent(updateNote.getContent());

        Note updatedNote = noteRepository.save(note);
        return NoteMapper.mapToNoteDto(updatedNote);
    }

    @Override
    public void deleteNote(UUID noteId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with ID: " + noteId));

        Meeting meeting = note.getMeeting();
        meeting.getNotes().remove(note);

        User contributor = note.getContributor();
        contributor.getContributedNotes().remove(note);

        noteRepository.deleteById(noteId);
    }
}
