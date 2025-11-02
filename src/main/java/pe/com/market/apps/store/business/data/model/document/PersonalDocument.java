package pe.com.market.apps.store.business.data.model.document;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "personal")
public class PersonalDocument {
    @Id
    private String id;
    private String companyId;
    private int documentTypeId;
    private String documentNumber;
    private String fatherLastName;
    private String motherLastName;
    private String name;
    private LocalDate birthDate;
    @Pattern(regexp = "^[M|F]$", message = "Genre is M or F")
    private String genre;
    private String cellphone;
    private String email;
    private String address;
    private LocalDateTime dateAt;
    private String personalRegisterId;
    private Boolean flagActive;
}
