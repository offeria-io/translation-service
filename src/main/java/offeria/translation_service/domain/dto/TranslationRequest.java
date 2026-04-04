package offeria.translation_service.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for lookup API.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TranslationRequest {

    @NotBlank(message = "Translation key is required")
    private String key;

    @NotBlank(message = "Target language is required")
    private String targetLang;

    private String sourceLang = "en"; // Default to English
}
