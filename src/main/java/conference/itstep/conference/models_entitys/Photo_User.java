package conference.itstep.conference.models_entitys;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "photo_users")
public class Photo_User {
    @Id
    private   String id;
    @Indexed( name  =  "user_id" , direction  =  IndexDirection. DESCENDING,unique = true)
    private String title;
    private String type_image;
    private Binary image;
}
