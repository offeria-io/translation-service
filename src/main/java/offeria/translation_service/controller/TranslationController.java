package offeria.translation_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import offeria.translation_service.domain.dto.TranslationDTO;
import offeria.translation_service.domain.dto.TranslationRequest;
import offeria.translation_service.service.TranslationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Controller for translation APIs.
 */
@RestController
@RequestMapping("/api/v1/translations")
@RequiredArgsConstructor
public class TranslationController {

    private final TranslationService translationService;

    /**
     * Look up a translation by key and language.
     * Request body contains key and target language.
     */
    @PostMapping("/lookup")
    public ResponseEntity<TranslationDTO> lookup(@Valid @RequestBody TranslationRequest request) {
        return ResponseEntity.ok(translationService.lookup(request));
    }

    /**
     * Get all translations.
     */
    @GetMapping
    public ResponseEntity<List<TranslationDTO>> getAll() {
        return ResponseEntity.ok(translationService.getAll());
    }

    /**
     * Upload translations from an Excel file.
     */
    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        translationService.loadFromExcel(file);
        return ResponseEntity.ok("File uploaded and translations loaded successfully.");
    }
}
