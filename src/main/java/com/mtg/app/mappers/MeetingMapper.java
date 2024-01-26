package com.mtg.app.mappers;

import com.mtg.app.dtos.MeetingDto;
import com.mtg.app.models.Meeting;
import com.mtg.app.models.User;
import com.mtg.app.repository.UserRepository;


public class MeetingMapper {
    public static MeetingDto mapToMeetingDto(Meeting meeting) {
        return new MeetingDto(
                meeting.getId(),
                meeting.getTitle(),
                meeting.getDateTime(),
                meeting.getOrganizer().getUserId(),
                meeting.getDescription()
        );
    }

    public static Meeting mapToMeeting(MeetingDto meetingDTO, UserRepository userRepository) {
        User organizer = userRepository.findById(meetingDTO.getOrganizerId())
                .orElseThrow(() -> new RuntimeException("Organizer not found with ID: " + meetingDTO.getOrganizerId()));

        return Meeting.builder()
                .title(meetingDTO.getTitle())
                .description(meetingDTO.getDescription())
                .dateTime(meetingDTO.getMeetingDateTime())
                .organizer(organizer)
                .build();
    }
}
