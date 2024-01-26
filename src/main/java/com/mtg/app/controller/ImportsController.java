package com.mtg.app.controller;

import com.mtg.app.dtos.ImportDto;
import com.mtg.app.dtos.NoteDto;
import com.mtg.app.services.ImportService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/imports")
@CrossOrigin(origins = "http://localhost:3000")
public class ImportsController {

    private ImportService importService;

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<ImportDto>> getAllImports(@PathVariable UUID userId) {
        List<ImportDto> imports = importService.getAllImports(userId);
        return ResponseEntity.ok(imports);
    }
}
