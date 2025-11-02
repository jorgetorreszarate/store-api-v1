package pe.com.market.apps.store.business.api.components;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pe.com.market.apps.store.business.data.model.document.*;
import pe.com.market.apps.store.business.data.repository.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Component
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserTypeRepository userTypeRepository;
    private final PersonalIdentityRepository personalIdentityRepository;
    private final PersonalRepository personalRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("run DataLoader CommandLineRunner...");

        try {
            registerUserTypes().subscribe();
            registerDocuments().subscribe();

            registerCompany()
                    .flatMap(this::registerPersonal)
                    .flatMap(this::registerUserAdmin)
                    .subscribe();

        } catch (Exception e) {
            log.error("### Ha ocurrido un error al registrar los datos de inicio");
            e.printStackTrace();
        }
    }

    // Tipos de username
    private Flux<UserTypeDocument> registerUserTypes() {
        return userTypeRepository.count()
                .filter(count -> count == 0) // solo si está vacío
                .thenMany(
                        Flux.just(
                                new UserTypeDocument(1, "ADMINISTRADOR"),
                                new UserTypeDocument(2, "VENDEDOR")
                        ).flatMap(userTypeRepository::save)
                );
    }

    // Documentos de personal
    private Flux<PersonalIdentityDocument> registerDocuments() {
        return personalIdentityRepository.count()
                .filter(count -> count == 0)
                .thenMany(
                        Flux.just(
                                new PersonalIdentityDocument(1, "DNI"),
                                new PersonalIdentityDocument(2, "CARNET DE EXTRANJERIA"),
                                new PersonalIdentityDocument(3, "PASAPORTE"),
                                new PersonalIdentityDocument(4, "RUC")
                        ).flatMap(personalIdentityRepository::save)
                );
    }

    private Mono<String> registerCompany() {
        return companyRepository.count()
                .filter(count -> count == 0)
                .flatMap(count -> {
                    CompanyDocument company = new CompanyDocument(
                            null,
                            4,
                            "201234567890",
                            "Galaxy Training",
                            "Galaxy Training",
                            true
                    );
                    return companyRepository.save(company)
                            .map(CompanyDocument::getId);
                });
    }

    private Mono<String> registerPersonal(String companyId) {
        return personalRepository.count()
                .filter(count -> count == 0)
                .then(
                        Mono.just(
                                        new PersonalDocument(
                                                null,
                                                companyId,
                                                1,
                                                "46669103",
                                                "Torres",
                                                "Zárate",
                                                "Jorge Luis",
                                                LocalDate.parse("1988-07-05"),
                                                "M",
                                                "995620211",
                                                "jorge.torres@gmail.com",
                                                "Av. Naranjal 123",
                                                LocalDateTime.parse("2025-10-30T00:00:00"),
                                                null,
                                                true
                                        )
                                )
                                .flatMap(personalRepository::save)
                                .map(PersonalDocument::getId) // Retorna el ID generado
                );
    }


    private Mono<UserDocument> registerUserAdmin(String personalId) {
        return userRepository.findById("ADMIN")
                .then(
                        Mono.just(
                                        new UserDocument(
                                                null,
                                                "ADMIN",
                                                personalId,
                                                passwordEncoder.encode("123"),
                                                1,
                                                true
                                        )
                                )
                                .flatMap(userRepository::save)
                );
    }
}
