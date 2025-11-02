package pe.com.market.apps.store.business.data.model.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "personal_identity")
public class PersonalIdentityDocument {
    @Id
    private int id;
    private String detail;
}
