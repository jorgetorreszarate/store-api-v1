package pe.com.market.apps.store.business.domain.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.com.market.apps.store.business.api.dto.request.UserPasswordRequest;
import pe.com.market.apps.store.business.api.dto.request.UserRequest;
import pe.com.market.apps.store.business.api.dto.response.UserResponse;
import pe.com.market.apps.store.business.api.dto.response.UserSingleResponse;
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
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<UserResponse> create(UserRequest userRequest) {
        return // Validar que el personal exista
                personalRepository.findById(userRequest.personalId())

                        .switchIfEmpty(Mono.error(new IllegalArgumentException("El personal no existe")))

                        // Validar que el tipo de username sea válido
                        .flatMap(document -> userTypeRepository.findById(userRequest.userTypeId()))
                        .switchIfEmpty(Mono.error(new IllegalArgumentException("El tipo de username no existe")))

                        // Validar que el username no exista
                        .flatMap(document ->
                                userRepository.findByUserId(userRequest.userId())
                                        .hasElements()
                                        .flatMap(exists -> {
                                            if (exists) {
                                                return Mono.error(new IllegalStateException("El username ya existe"));
                                            }
                                            return Mono.just(userRequest);
                                        })
                        )
                        .map(userMapper::toDocument)
                        .doOnNext(user -> {
                            user.setUserId(userRequest.userId().toUpperCase());
                            user.setPassword(passwordEncoder.encode(userRequest.password()));
                            user.setFlagActive(true);
                        })
                        .flatMap(userRepository::save)
                        .flatMap(this::mapToResponse);
    }

    @Override
    public Flux<UserResponse> findByPersonalId(String personalId) {
        return userRepository.findByPersonalId(personalId)
                .flatMap(this::mapToResponse);
    }

    /*@Override
    public Mono<UserSingleResponse> single(String userId) {
        return userRepository.findByUserId(userId)
                .next()
                .flatMap();
    }*/

    @Override
    public Mono<UserResponse> update(UserRequest userRequest) {
        return userRepository.findByUserId(userRequest.userId())
                .next()
                .switchIfEmpty(Mono.error(new IllegalArgumentException("El username no existe")))
                .doOnNext(user -> {
                    user.setUserTypeId(userRequest.userTypeId());
                    user.setPassword(passwordEncoder.encode(userRequest.password()));
                    user.setFlagActive(userRequest.flagActive());
                })
                .flatMap(userRepository::save)
                .flatMap(this::mapToResponse);
    }

    @Override
    public Mono<Boolean> updatePassword(UserPasswordRequest userPasswordRequest) {
        return userRepository.findByUserId(userPasswordRequest.userId())
                .next()
                .switchIfEmpty(Mono.error(new IllegalArgumentException("El username no existe")))
                .flatMap(user -> {
                    user.setPassword(passwordEncoder.encode(userPasswordRequest.password()));
                    return userRepository.save(user);
                })
                .thenReturn(true);
    }

    @Override
    public Mono<Boolean> delete(String id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("El username no existe")))
                .flatMap(userRepository::delete)
                .thenReturn(true);
    }

    private Mono<UserResponse> mapToResponse(UserDocument user) {
        return Mono.zip(
                personalRepository.findById(user.getPersonalId())
                        .switchIfEmpty(Mono.error(new Exception("### Personal no encontrado"))),
                userTypeRepository.findById(user.getUserTypeId())
                        .switchIfEmpty(Mono.error(new Exception("### Tipo de username no encontrado")))
        ).map(tuple ->
                userMapper.toResponseFull(user, tuple.getT1(), tuple.getT2()));
    }
}
