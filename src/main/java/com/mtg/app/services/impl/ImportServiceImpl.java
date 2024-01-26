package com.mtg.app.services.impl;

import com.mtg.app.dtos.ImportDto;
import com.mtg.app.mappers.ImportMapper;
import com.mtg.app.mappers.NoteMapper;
import com.mtg.app.models.Import;
import com.mtg.app.models.Note;
import com.mtg.app.models.User;
import com.mtg.app.repository.ImportRepository;
import com.mtg.app.services.ImportService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ImportServiceImpl implements ImportService {

    private final ImportRepository importRepository;

    public ImportServiceImpl(ImportRepository importRepository) {
        this.importRepository = importRepository;
    }

    @Override
    public void importData(User user, String title, String content) {
        Import importData = new Import();
        importData.setUser(user);
        importData.setTitle(title);
        importData.setContent(content);
        importRepository.save(importData);
    }

    @Override
    public List<ImportDto> getAllImports(UUID userId) {
        List<Import> imports = importRepository.findByUserUserId(userId);
        return imports.stream().map(ImportMapper::mapToImportDto).collect(Collectors.toList());
    }
}
