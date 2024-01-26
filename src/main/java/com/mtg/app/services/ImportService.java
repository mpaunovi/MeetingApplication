package com.mtg.app.services;

import com.mtg.app.dtos.ImportDto;
import com.mtg.app.models.User;

import java.util.List;
import java.util.UUID;

public interface ImportService {

    void importData(User user, String title, String content);

    List<ImportDto> getAllImports(UUID userId);
}
