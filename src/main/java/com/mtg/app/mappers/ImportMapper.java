package com.mtg.app.mappers;

import com.mtg.app.dtos.ImportDto;
import com.mtg.app.models.Import;
import com.mtg.app.models.User;
import com.mtg.app.repository.UserRepository;

public class ImportMapper {

    public static ImportDto mapToImportDto(Import importData) {
        return new ImportDto(
                importData.getUser().getUserId(),
                importData.getTitle(),
                importData.getContent()
        );
    }

    public static Import mapToImport(ImportDto importDto, UserRepository userRepository) {

        User user = userRepository.findById(importDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + importDto.getUserId()));

        return Import.builder()
                .user(user)
                .title(importDto.getTitle())
                .content(importDto.getContent())
                .build();
    }
}
