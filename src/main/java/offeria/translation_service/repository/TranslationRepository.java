package offeria.translation_service.repository;

import offeria.translation_service.domain.entity.Translation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing Translation entities.
 */
@Repository
public interface TranslationRepository extends JpaRepository<Translation, Long> {

    /**
     * Find a translation by its key and languages.
     */
    Optional<Translation> findByKeyAndSourceLangAndTargetLang(String key, String sourceLang, String targetLang);

    /**
     * Find a translation by its key and target language.
     */
    Optional<Translation> findByKeyAndTargetLang(String key, String targetLang);
}
