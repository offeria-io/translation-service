package offeria.translation_service.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Entity representing a translation entry in the database.
 */
@Entity
@Table(name = "translations", indexes = {
    @Index(name = "idx_translation_key", columnList = "translation_key"),
    @Index(name = "idx_source_lang", columnList = "source_lang"),
    @Index(name = "idx_target_lang", columnList = "target_lang")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Translation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The unique key or the English text to be translated
    @Column(name = "translation_key", nullable = false, columnDefinition = "TEXT")
    private String key;

    // The translated text (e.g., in Arabic)
    @Column(name = "translated_value", nullable = false, columnDefinition = "TEXT")
    private String value;

    @Column(name = "source_lang", nullable = false, length = 10)
    private String sourceLang;

    @Column(name = "target_lang", nullable = false, length = 10)
    private String targetLang;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
