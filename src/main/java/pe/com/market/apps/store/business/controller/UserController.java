/*package pe.com.market.apps.store.business.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.market.apps.store.business.api.dto.request.UserRequest;
import pe.com.market.apps.store.business.api.dto.response.UserResponse;
import pe.com.market.apps.store.business.domain.services.UserService;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/by-personal/{personalId}")
    public Mono<ResponseEntity<List<UserResponse>>> findAllByPersonal(@PathVariable String personalId) {
        try {
            return userService.findByPersonalId(personalId)
                    .collectList()
                    .map(users -> {
                        if (users.isEmpty()) {
                            return ResponseEntity.noContent().build();
                        }

                        return ResponseEntity.ok(users);
                    });
        } catch (Exception e) {
            log.error(e.getMessage());
            return Mono.just(ResponseEntity.internalServerError().build());
        }
    }

    @PostMapping
    public Mono<ResponseEntity<UserResponse>> create(@RequestBody UserRequest user) {
        try {
            return userService.create(user)
                    .map(ResponseEntity::ok)
                    .defaultIfEmpty(ResponseEntity.notFound().build());
        } catch (Exception e) {
            log.error(e.getMessage());
            return Mono.just(ResponseEntity.internalServerError().build());
        }
    }
}
*/