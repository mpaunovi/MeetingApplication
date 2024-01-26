package com.mtg.app.dtos.response;

import com.mtg.app.dtos.NoteDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteResponseDto {

    private NoteDto noteDto;
    private String message;
}
