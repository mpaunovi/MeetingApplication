package com.mtg.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MeetingDto {

    private UUID meetingId;
    private String title;
    private LocalDateTime meetingDateTime;
    private UUID organizerId;
    private String description;
}
