package pe.com.market.apps.store.business.domain.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pe.com.market.apps.store.business.api.dto.request.UserRequest;
import pe.com.market.apps.store.business.api.dto.response.UserResponse;
import pe.com.market.apps.store.business.api.dto.response.UserSingleResponse;
import pe.com.market.apps.store.business.data.model.document.CompanyDocument;
import pe.com.market.apps.store.business.data.model.document.PersonalDocument;
import pe.com.market.apps.store.business.data.model.document.UserDocument;
import pe.com.market.apps.store.business.data.model.document.UserTypeDocument;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserMapper {
    UserDocument toDocument(UserRequest request);

    UserResponse toResponse(UserDocument document);

    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "personalId", source = "personal.id")
    @Mapping(target = "fullName", source = "personal", qualifiedByName = "concatFullName")
    @Mapping(target = "userTypeId", source = "userType.id")
    @Mapping(target = "userType", source = "userType.detail")
    @Mapping(target = "flagActive", source = "user.flagActive")
    UserResponse toResponseFull(
            UserDocument user,
            PersonalDocument personal,
            UserTypeDocument userType
    );

    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "companyId", source = "company.id")
    @Mapping(target = "company", source = "company.companyName")
    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "personalId", source = "personal.id")
    @Mapping(target = "name", source = "personal.name")
    @Mapping(target = "fatherLastName", source = "personal.fatherLastName")
    @Mapping(target = "motherLastName", source = "personal.motherLastName")
    @Mapping(target = "fullName", source = "personal", qualifiedByName = "concatFullName")
    @Mapping(target = "email", source = "personal.email")
    @Mapping(target = "userTypeId", source = "userType.id")
    @Mapping(target = "userType", source = "userType.detail")
    @Mapping(target = "password", source = "user.password")
    @Mapping(target = "flagActive", source = "user.flagActive")
    UserSingleResponse toSingle(
            UserDocument user,
            PersonalDocument personal,
            UserTypeDocument userType,
            CompanyDocument company
    );

    @Named("concatFullName")
    default String concatFullName(PersonalDocument personal) {
        return String.format("%s %s %s",
                personal.getName(),
                personal.getFatherLastName(),
                personal.getMotherLastName()
        ).trim();
    }
}
