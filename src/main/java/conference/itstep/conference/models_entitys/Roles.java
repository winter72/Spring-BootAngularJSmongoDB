package conference.itstep.conference.models_entitys;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "roles")
public class Roles implements Serializable, GrantedAuthority {
    @Id
    String _id;
    @Indexed( name  =  "role_name_index" , direction  =  IndexDirection. DESCENDING,unique = true)
    private String rolename;
    @Override
    public String getAuthority() {
        return rolename;
    }
}
