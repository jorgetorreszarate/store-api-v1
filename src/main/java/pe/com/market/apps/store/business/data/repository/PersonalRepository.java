package pe.com.market.apps.store.business.data.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pe.com.market.apps.store.business.data.model.document.PersonalDocument;

public interface PersonalRepository
        extends ReactiveMongoRepository<PersonalDocument, String> {
}
