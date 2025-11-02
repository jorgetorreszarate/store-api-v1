package pe.com.market.apps.store.business.data.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pe.com.market.apps.store.business.data.model.document.UserDocument;
import reactor.core.publisher.Flux;

public interface UserRepository
        extends ReactiveMongoRepository<UserDocument, String> {
    Flux<UserDocument> findByPersonalId(String personalId);

    //@Query("{ 'userId': ?0 }")
    Flux<UserDocument> findByUserId(String userId);
}
