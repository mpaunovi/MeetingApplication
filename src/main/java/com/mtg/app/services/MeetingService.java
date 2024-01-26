package com.mtg.app.services;

import com.mtg.app.dtos.MeetingDto;
import com.mtg.app.dtos.request.MeetingUpdateDto;
import com.mtg.app.repository.NoteRepository;
import com.mtg.app.repository.UserRepository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface MeetingService {

    void importMeetingsFromCSV(MultipartFile file);

    MeetingDto createMeeting(MeetingDto meetingDto, UserRepository userRepository);

    MeetingDto getMeetingById(UUID meetingId);

    List<MeetingDto> getAllMeetingsByUser(UUID userId);

    MeetingDto updateMeeting(UUID meetingId, MeetingUpdateDto updateMeeting);

    void deleteMeeting(UUID meetingId);
}
