package offeria.translation_service.service;

import offeria.translation_service.domain.dto.TranslationDTO;
import offeria.translation_service.domain.dto.TranslationRequest;
import offeria.translation_service.domain.entity.Translation;
import offeria.translation_service.exception.TranslationServiceException;
import offeria.translation_service.mapper.TranslationMapper;
import offeria.translation_service.repository.TranslationRepository;
import offeria.translation_service.service.impl.TranslationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TranslationServiceTest {

    @Mock
    private TranslationRepository repository;

    @Mock
    private TranslationMapper mapper;

    @InjectMocks
    private TranslationServiceImpl translationService;

    private Translation translation;
    private TranslationDTO translationDTO;
    private TranslationRequest request;

    @BeforeEach
    void setUp() {
        translation = Translation.builder()
                .id(1L)
                .key("hello")
                .value("مرحبا")
                .sourceLang("en")
                .targetLang("ar")
                .build();

        translationDTO = TranslationDTO.builder()
                .id(1L)
                .key("hello")
                .value("مرحبا")
                .sourceLang("en")
                .targetLang("ar")
                .build();

        request = TranslationRequest.builder()
                .key("hello")
                .targetLang("ar")
                .build();
    }

    @Test
    void lookup_Success() {
        when(repository.findByKeyAndTargetLang("hello", "ar")).thenReturn(Optional.of(translation));
        when(mapper.toDTO(translation)).thenReturn(translationDTO);

        TranslationDTO result = translationService.lookup(request);

        assertNotNull(result);
        assertEquals("مرحبا", result.getValue());
        verify(repository, times(1)).findByKeyAndTargetLang("hello", "ar");
    }

    @Test
    void lookup_NotFound() {
        when(repository.findByKeyAndTargetLang("hello", "ar")).thenReturn(Optional.empty());

        TranslationServiceException exception = assertThrows(TranslationServiceException.class, () -> {
            translationService.lookup(request);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertTrue(exception.getMessage().contains("Translation not found"));
    }
}
