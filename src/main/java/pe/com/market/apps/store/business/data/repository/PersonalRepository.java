package pe.com.market.apps.store.business.data.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pe.com.market.apps.store.business.data.model.document.PersonalDocument;
import reactor.core.publisher.Flux;

public interface PersonalRepository
        extends ReactiveMongoRepository<PersonalDocument, String> {

    @Query("{ '$or': [ { 'fatherLastName': { $regex: ?0, $options: 'i' } }, { 'motherLastName': { $regex: ?0, $options: 'i' } }, { 'name': { $regex: ?0, $options: 'i' } } ] }")
    Flux<PersonalDocument> search(String value);

}
