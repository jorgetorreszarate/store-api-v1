package pe.com.market.apps.store.business.data.model.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class UserDocument {
    @Id
    private String id;

    //    @Field("IdUsuario")
    private String userId;

    //    @Field("IdPersonal")
    private String personalId;

    //    @Field("Clave")
    private String password;

    //    @Field("IdTipo")
    private int userTypeId;

    //    @Field("FlagActivo")
    private boolean flagActive;
}
