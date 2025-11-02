package pe.com.market.apps.store.business.domain.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pe.com.market.apps.store.business.api.dto.request.UserRequest;
import pe.com.market.apps.store.business.api.dto.response.UserResponse;
import pe.com.market.apps.store.business.data.model.document.PersonalDocument;
import pe.com.market.apps.store.business.data.model.document.UserDocument;
import pe.com.market.apps.store.business.data.model.document.UserTypeDocument;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserMapper {
    UserDocument toDocument(UserRequest request);

    UserResponse toResponse(UserDocument document);

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

    @Named("concatFullName")
    default String concatFullName(PersonalDocument personal) {
        return String.format("%s %s %s",
                personal.getName(),
                personal.getFatherLastName(),
                personal.getMotherLastName()
        ).trim();
    }
}
