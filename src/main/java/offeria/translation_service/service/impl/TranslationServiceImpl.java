package offeria.translation_service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import offeria.translation_service.domain.dto.TranslationDTO;
import offeria.translation_service.domain.dto.TranslationRequest;
import offeria.translation_service.domain.entity.Translation;
import offeria.translation_service.exception.TranslationServiceException;
import offeria.translation_service.mapper.TranslationMapper;
import offeria.translation_service.repository.TranslationRepository;
import offeria.translation_service.service.TranslationService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementation of TranslationService.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TranslationServiceImpl implements TranslationService {

    private final TranslationRepository repository;
    private final TranslationMapper mapper;

    @Override
    public TranslationDTO lookup(TranslationRequest request) {
        log.info("Looking up translation for key: {} in language: {}", request.getKey(), request.getTargetLang());
        
        Translation translation = repository.findByKeyAndTargetLang(request.getKey(), request.getTargetLang())
                .orElseThrow(() -> new TranslationServiceException(
                        "Translation not found for key: " + request.getKey(), HttpStatus.NOT_FOUND));
        
        return mapper.toDTO(translation);
    }

    @Override
    public List<TranslationDTO> getAll() {
        return mapper.toDTOList(repository.findAll());
    }

    @Override
    @Transactional
    public void loadFromExcel(MultipartFile file) {
        if (file.isEmpty()) {
            throw new TranslationServiceException("File is empty", HttpStatus.BAD_REQUEST);
        }

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            // Skip header
            if (rows.hasNext()) {
                rows.next();
            }

            List<Translation> translationsToSave = new ArrayList<>();
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                
                // Expecting structure: Key (A), Value (B), SourceLang (C), TargetLang (D)
                String key = getCellValueAsString(currentRow.getCell(0));
                String value = getCellValueAsString(currentRow.getCell(1));
                String sourceLang = getCellValueAsString(currentRow.getCell(2));
                String targetLang = getCellValueAsString(currentRow.getCell(3));

                if (key != null && value != null) {
                    Translation translation = Translation.builder()
                            .key(key)
                            .value(value)
                            .sourceLang(sourceLang != null ? sourceLang : "en")
                            .targetLang(targetLang != null ? targetLang : "ar")
                            .build();
                    translationsToSave.add(translation);
                }
            }
            
            repository.saveAll(translationsToSave);
            log.info("Successfully loaded {} translations from Excel", translationsToSave.size());
            
        } catch (IOException e) {
            log.error("Failed to parse Excel file", e);
            throw new TranslationServiceException("Failed to parse Excel file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) return null;
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            default -> null;
        };
    }
}
