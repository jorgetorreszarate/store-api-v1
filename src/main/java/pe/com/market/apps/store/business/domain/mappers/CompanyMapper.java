package pe.com.market.apps.store.business.domain.mappers;

import org.mapstruct.Mapper;
import pe.com.market.apps.store.business.api.dto.response.CompanyResponse;
import pe.com.market.apps.store.business.data.model.document.CompanyDocument;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CompanyMapper {
    CompanyResponse toResponse(CompanyDocument document);
}
