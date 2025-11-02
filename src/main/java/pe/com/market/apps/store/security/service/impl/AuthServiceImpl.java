package pe.com.market.apps.store.security.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.com.market.apps.store.business.api.dto.response.UserResponse;
import pe.com.market.apps.store.business.api.dto.response.UserSingleResponse;
import pe.com.market.apps.store.business.data.model.document.UserDocument;
import pe.com.market.apps.store.business.data.repository.CompanyRepository;
import pe.com.market.apps.store.business.data.repository.PersonalRepository;
import pe.com.market.apps.store.business.data.repository.UserRepository;
import pe.com.market.apps.store.business.data.repository.UserTypeRepository;
import pe.com.market.apps.store.business.domain.mappers.UserMapper;
import pe.com.market.apps.store.security.api.dto.request.AuthRequest;
import pe.com.market.apps.store.security.api.dto.response.AuthResponse;
import pe.com.market.apps.store.security.api.exceptions.AuthException;
import pe.com.market.apps.store.security.api.filter.JwtProviderUtils;
import pe.com.market.apps.store.security.service.AuthService;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
    private final PersonalRepository personalRepository;
    private final UserTypeRepository userTypeRepository;
    private final CompanyRepository companyRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtProviderUtils jwtProviderUtils;
    private final UserMapper userMapper;

	@Override
	public Mono<AuthResponse> login(AuthRequest request) {
		return userRepository
				.findByUserId(request.username().toUpperCase())
                .next()
				.filter(user ->
						passwordEncoder.matches(request.password(), user.getPassword())
								&& user.isFlagActive())
                .flatMap(this::mapToResponse)
                .map(user ->
						new AuthResponse(jwtProviderUtils.generateToken(user)))
				.switchIfEmpty(Mono.error(new AuthException("Credenciales incorrectas o username inactivo.")));
	}

    private Mono<UserSingleResponse> mapToResponse(UserDocument user) {
        return personalRepository.findById(user.getPersonalId())
                .flatMap(personal ->
                        Mono.zip(
                                Mono.just(personal),
                                userTypeRepository.findById(user.getUserTypeId()),
                                companyRepository.findById(personal.getCompanyId())
                        )
                )
                .map(tuple ->
                        userMapper.toSingle(user, tuple.getT1(), tuple.getT2(), tuple.getT3())
                );
    }
}
