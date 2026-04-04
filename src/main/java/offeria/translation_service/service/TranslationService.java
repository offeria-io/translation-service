package offeria.translation_service.service;

import offeria.translation_service.domain.dto.TranslationDTO;
import offeria.translation_service.domain.dto.TranslationRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Service interface for translation operations.
 */
public interface TranslationService {

    /**
     * Look up a translation by key and language.
     */
    TranslationDTO lookup(TranslationRequest request);

    /**
     * Load translations from an Excel file.
     */
    void loadFromExcel(MultipartFile file);

    /**
     * Get all translations.
     */
    List<TranslationDTO> getAll();
}
