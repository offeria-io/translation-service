package offeria.translation_service.mapper;

import offeria.translation_service.domain.dto.TranslationDTO;
import offeria.translation_service.domain.entity.Translation;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * MapStruct mapper for converting between Translation entity and DTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TranslationMapper {

    TranslationDTO toDTO(Translation translation);

    Translation toEntity(TranslationDTO translationDTO);

    List<TranslationDTO> toDTOList(List<Translation> translations);
}
