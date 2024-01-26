package com.mtg.app.mappers;

import com.mtg.app.dtos.NoteDto;
import com.mtg.app.models.Meeting;
import com.mtg.app.models.Note;
import com.mtg.app.models.User;
import com.mtg.app.repository.MeetingRepository;
import com.mtg.app.repository.UserRepository;

public class NoteMapper {

    public static NoteDto mapToNoteDto(Note note) {
        return new NoteDto(
                note.getId(),
                note.getMeeting().getId(),
                note.getContributor().getUserId(),
                note.getTitle(),
                note.getContent()
        );
    }

    public static Note mapToNote(NoteDto noteDto, MeetingRepository meetingRepository, UserRepository userRepository) {
        User contributor = userRepository.findById(noteDto.getContributorId())
                .orElseThrow(() -> new RuntimeException("Organizer not found with ID: " + noteDto.getContributorId()));

        Meeting meeting = meetingRepository.findById(noteDto.getMeetingId())
                .orElseThrow(() -> new RuntimeException("Organizer not found with ID: " + noteDto.getMeetingId()));

        return Note.builder()
                .meeting(meeting)
                .contributor(contributor)
                .title(noteDto.getTitle())
                .content(noteDto.getContent())
                .build();
    }
}
