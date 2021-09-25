package conference.itstep.conference.models_entitys;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "conferences")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConferencesForUser {
    @Id
    private String _id;

    @Indexed( name  =  "title_index" , direction  =  IndexDirection. DESCENDING,unique = true)
    private String title;

    @Indexed( name  =  "data_index" , direction  =  IndexDirection. DESCENDING)
    private Date dtc;

    @DBRef(lazy = true)
    private Set<Users> user_id=new HashSet();
}
