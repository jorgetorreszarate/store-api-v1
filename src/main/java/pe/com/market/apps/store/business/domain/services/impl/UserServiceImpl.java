package pe.com.market.apps.store.business.domain.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pe.com.market.apps.store.business.api.dto.request.UserPasswordRequest;
import pe.com.market.apps.store.business.api.dto.request.UserRequest;
import pe.com.market.apps.store.business.api.dto.response.UserResponse;
import pe.com.market.apps.store.business.data.model.document.UserDocument;
import pe.com.market.apps.store.business.data.repository.PersonalRepository;
import pe.com.market.apps.store.business.data.repository.UserRepository;
import pe.com.market.apps.store.business.data.repository.UserTypeRepository;
import pe.com.market.apps.store.business.domain.mappers.UserMapper;
import pe.com.market.apps.store.business.domain.services.UserService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PersonalRepository personalRepository;
    private final UserTypeRepository userTypeRepository;
    private final UserMapper userMapper;

    @Override
    public Mono<UserResponse> create(UserRequest userRequest) {
        return // Validar que el personal exista
                personalRepository.findById(userRequest.personalId())

                        .switchIfEmpty(Mono.error(new IllegalArgumentException("El personal no existe")))

                        // Validar que el tipo de usuario sea válido
                        .flatMap(document -> userTypeRepository.findById(userRequest.userTypeId()))
                        .switchIfEmpty(Mono.error(new IllegalArgumentException("El tipo de usuario no existe")))

                        // Validar que el usuario no exista
                        .flatMap(document ->
                                userRepository.findByUserId(userRequest.userId())
                                        .hasElements()
                                        .flatMap(exists -> {
                                            if (exists) {
                                                return Mono.error(new IllegalStateException("El usuario ya existe"));
                                            }
                                            return Mono.just(userRequest);
                                        })
                        )
                        .map(userMapper::toDocument)
                        .doOnNext(user -> user.setFlagActive(true))
                        .flatMap(userRepository::save)
                        .flatMap(this::mapToResponse);
    }

    @Override
    public Flux<UserResponse> findByPersonalId(String personalId) {
        return userRepository.findByPersonalId(personalId)
                .flatMap(this::mapToResponse);
    }

    @Override
    public Mono<UserResponse> update(UserRequest userDocument) {
        return userRepository.findByUserId(userDocument.userId())
                .next()
                .switchIfEmpty(Mono.error(new IllegalArgumentException("El usuario no existe")))
                .doOnNext(user -> {
                    user.setUserTypeId(userDocument.userTypeId());
                    user.setPassword(userDocument.password());
                    user.setFlagActive(userDocument.flagActive());
                })
                .flatMap(userRepository::save)
                .flatMap(this::mapToResponse);
    }

    @Override
    public Mono<Boolean> updatePassword(UserPasswordRequest userPasswordRequest) {
        return userRepository.findByUserId(userPasswordRequest.userId())
                .next()
                .switchIfEmpty(Mono.error(new IllegalArgumentException("El usuario no existe")))
                .flatMap(user -> {
                    user.setPassword(userPasswordRequest.password());
                    return userRepository.save(user);
                })
                .thenReturn(true);
    }

    @Override
    public Mono<Boolean> delete(String userId) {
        return userRepository.findByUserId(userId)
                .next()
                .switchIfEmpty(Mono.error(new IllegalArgumentException("El usuario no existe")))
                .flatMap(userRepository::delete)
                .thenReturn(true);
    }

    private Mono<UserResponse> mapToResponse(UserDocument user) {
        return Mono.zip(
                personalRepository.findById(user.getPersonalId())
                        .switchIfEmpty(Mono.error(new Exception("### Personal no encontrado"))),
                userTypeRepository.findById(user.getUserTypeId())
                        .switchIfEmpty(Mono.error(new Exception("### Tipo de usuario no encontrado")))
        ).map(tuple ->
                userMapper.toResponseFull(user, tuple.getT1(), tuple.getT2()));
    }
}
