package com.mtg.app.dtos.response;

import com.mtg.app.dtos.MeetingDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MeetingResponseDto {

    private MeetingDto meetingDto;
    private String message;
}
