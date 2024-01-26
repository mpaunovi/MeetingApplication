package com.mtg.app.dtos;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CSVMeetingDto {
    @CsvBindByName(column = "UserEmail")
    private String userEmail;

    @CsvBindByName(column = "Title")
    private String title;

    @CsvBindByName(column = "Content")
    private String content;
}
