package pe.com.market.apps.store.business.data.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pe.com.market.apps.store.business.data.model.document.CompanyDocument;

public interface CompanyRepository
        extends ReactiveMongoRepository<CompanyDocument, String> {
}
