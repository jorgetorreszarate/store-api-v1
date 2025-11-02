package pe.com.market.apps.store.business.domain.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pe.com.market.apps.store.business.api.dto.request.PersonalRequest;
import pe.com.market.apps.store.business.api.dto.response.PersonalResponse;
import pe.com.market.apps.store.business.data.model.document.CompanyDocument;
import pe.com.market.apps.store.business.data.model.document.PersonalDocument;
import pe.com.market.apps.store.business.data.model.document.PersonalIdentityDocument;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface PersonalMapper {
    PersonalDocument toDocument(PersonalRequest request);

    PersonalResponse toResponse(PersonalDocument document);

    @Mapping(target = "personalId", source = "personal.id")
    @Mapping(target = "companyId", source = "company.id")
    @Mapping(target = "companyName", source = "company.companyName")
    @Mapping(target = "documentTypeId", source = "identity.id")
    @Mapping(target = "documentType", source = "identity.detail")
    @Mapping(target = "documentNumber", source = "personal.documentNumber")
    @Mapping(target = "flagActive", source = "personal.flagActive")
    PersonalResponse toResponseFull(
            PersonalDocument personal,
            CompanyDocument company,
            PersonalIdentityDocument identity
    );
}
