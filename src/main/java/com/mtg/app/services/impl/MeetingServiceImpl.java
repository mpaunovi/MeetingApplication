package com.mtg.app.services.impl;

import com.mtg.app.dtos.CSVMeetingDto;
import com.mtg.app.dtos.MeetingDto;
import com.mtg.app.dtos.request.MeetingUpdateDto;
import com.mtg.app.exception.ResourceNotFoundException;
import com.mtg.app.mappers.MeetingMapper;
import com.mtg.app.models.Meeting;
import com.mtg.app.models.User;
import com.mtg.app.repository.MeetingRepository;
import com.mtg.app.repository.NoteRepository;
import com.mtg.app.repository.UserRepository;
import com.mtg.app.services.ImportService;
import com.mtg.app.services.MeetingService;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class MeetingServiceImpl implements MeetingService {

    private static final Logger logger = LoggerFactory.getLogger(MeetingServiceImpl.class);

    private final MeetingRepository meetingRepository;
    private final UserRepository userRepository;
    private final ImportService importService;

    @Override
    public void importMeetingsFromCSV(MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CSVReader csvReader = new CSVReaderBuilder(reader).build();

            CsvToBean<CSVMeetingDto> csvToBean = new CsvToBeanBuilder<CSVMeetingDto>(csvReader)
                    .withType(CSVMeetingDto.class)
                    .build();

            List<CSVMeetingDto> csvMeetingDtos = csvToBean.parse();

            for (CSVMeetingDto csvMeetingDto : csvMeetingDtos) {
                User user = userRepository.findByEmail(csvMeetingDto.getUserEmail())
                        .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + csvMeetingDto.getUserEmail()));

                Meeting meeting = new Meeting();
                meeting.setTitle(csvMeetingDto.getTitle());
                meeting.setDescription(csvMeetingDto.getContent());
                meeting.setOrganizer(user);
                meeting.setDateTime(LocalDateTime.now());

                Meeting savedMeeting = meetingRepository.save(meeting);

                user.getOrganizedMeetings().add(savedMeeting);
                userRepository.save(user);

                importService.importData(user, csvMeetingDto.getTitle(), csvMeetingDto.getContent());
            }
        } catch (IOException e) {
            logger.error("Failed to import CSV file.");
        }
    }

    @Override
    public MeetingDto createMeeting(MeetingDto meetingDto, UserRepository userRepository) {
        Meeting savedMeeting = meetingRepository.save(MeetingMapper.mapToMeeting(meetingDto, userRepository));

        User organizer = userRepository.findById(meetingDto.getOrganizerId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + meetingDto.getOrganizerId()));

        organizer.getOrganizedMeetings().add(savedMeeting);
        userRepository.save(organizer);

        return MeetingMapper.mapToMeetingDto(savedMeeting);
    }

    @Override
    public MeetingDto getMeetingById(UUID meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Meeting with the given id " + meetingId + "doesn't exists!"));

        return MeetingMapper.mapToMeetingDto(meeting);
    }

    @Override
    public List<MeetingDto> getAllMeetingsByUser(UUID userId) {
        List<Meeting> meetings = meetingRepository.findByOrganizerUserId(userId);
        return meetings.stream()
                .map(MeetingMapper::mapToMeetingDto)
                .collect(Collectors.toList());
    }

    @Override
    public MeetingDto updateMeeting(UUID meetingId, MeetingUpdateDto updateMeeting) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Meeting with the given id " + meetingId + "doesn't exists!"));

        meeting.setTitle(updateMeeting.getTitle());
        meeting.setDescription(updateMeeting.getDescription());

        Meeting updatedMeeting = meetingRepository.save(meeting);
        return MeetingMapper.mapToMeetingDto(updatedMeeting);
    }

    @Override
    public void deleteMeeting(UUID meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Meeting with the given id " + meetingId + "doesn't exists!"));

        User organizer = meeting.getOrganizer();
        organizer.getOrganizedMeetings().remove(meeting);
        userRepository.save(organizer);

        meetingRepository.deleteById(meetingId);
    }
}
