package offeria.translation_service.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Translation data.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TranslationDTO {
    private Long id;
    private String key;
    private String value;
    private String sourceLang;
    private String targetLang;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
