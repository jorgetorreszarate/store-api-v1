package pe.com.market.apps.store.business.data.model.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "company")
public class CompanyDocument {
    // private int companyId;
    private String id;
    private int documentTypeId;
    private String documentNumber;
    private String companyName;
    private String tradeName;
    private boolean flagActive;
}
