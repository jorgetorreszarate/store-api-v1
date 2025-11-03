package pe.com.market.apps.store.business.domain.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pe.com.market.apps.store.business.api.dto.response.CompanyResponse;
import pe.com.market.apps.store.business.data.model.document.CompanyDocument;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CompanyMapper {
    @Mapping(target = "companyId", source = "id")
    CompanyResponse toResponse(CompanyDocument document);
}
