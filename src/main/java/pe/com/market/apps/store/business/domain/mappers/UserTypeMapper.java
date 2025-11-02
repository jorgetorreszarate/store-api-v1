package pe.com.market.apps.store.business.domain.mappers;

import org.mapstruct.Mapper;
import pe.com.market.apps.store.business.api.dto.response.UserTypeResponse;
import pe.com.market.apps.store.business.data.model.document.UserTypeDocument;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserTypeMapper {
    UserTypeResponse toResponse(UserTypeDocument document);
}
