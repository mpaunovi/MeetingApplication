package com.mtg.app.controller;

import com.mtg.app.dtos.MeetingDto;
import com.mtg.app.dtos.request.MeetingUpdateDto;
import com.mtg.app.dtos.response.MeetingResponseDto;
import com.mtg.app.repository.NoteRepository;
import com.mtg.app.repository.UserRepository;
import com.mtg.app.services.MeetingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/meetings")
@CrossOrigin(origins = "http://localhost:3000")
public class MeetingController {

    private MeetingService meetingService;
    private UserRepository userRepository;

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<MeetingDto>> getMeetingsForParticipant(@PathVariable UUID userId) {
        List<MeetingDto> meetings = meetingService.getAllMeetingsByUser(userId);
        return ResponseEntity.ok(meetings);
    }

    @GetMapping("/{meetingId}")
    public MeetingDto getMeetingDetails(@PathVariable UUID meetingId) {
        return meetingService.getMeetingById(meetingId);
    }

    @PostMapping("/import")
    public ResponseEntity<String> importMeetings(@RequestParam("file") MultipartFile file) {
        meetingService.importMeetingsFromCSV(file);
        return ResponseEntity.ok("Meetings imported successfully.");
    }

    @PostMapping
    public ResponseEntity<MeetingResponseDto> createMeeting(@RequestBody MeetingDto meetingDTO) {
        MeetingDto createdMeeting = meetingService.createMeeting(meetingDTO, userRepository);

        MeetingResponseDto response = new MeetingResponseDto();
        response.setMeetingDto(createdMeeting);
        response.setMessage("Meeting created successfully.");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{meetingId}")
    public ResponseEntity<MeetingResponseDto> updateMeeting(@PathVariable UUID meetingId, @RequestBody MeetingUpdateDto updateMeeting) {
        MeetingDto updatedMeeting = meetingService.updateMeeting(meetingId, updateMeeting);

        MeetingResponseDto response = new MeetingResponseDto();
        response.setMeetingDto(updatedMeeting);
        response.setMessage("Meeting updated successfully.");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{meetingId}")
    public ResponseEntity<String> deleteMeeting(@PathVariable UUID meetingId) {
        meetingService.deleteMeeting(meetingId);
        return ResponseEntity.ok("Meeting deleted successfully.");
    }
}
