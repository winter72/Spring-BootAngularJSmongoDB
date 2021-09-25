package conference.itstep.conference.models_entitys;

import lombok.*;
//import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
//import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")

public class Users implements UserDetails,Serializable {


    @Id
    private   String id;

    @DBRef
    private List<Roles> authorities;


    @Indexed( name  =  "user_name_index" , direction  =  IndexDirection. DESCENDING)
    @com.mongodb.lang.NonNull
    private   String username;

    @com.mongodb.lang.NonNull
    private String password;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;

    @Indexed( name  =  "user_surname_index" , direction  =  IndexDirection. DESCENDING)
    @com.mongodb.lang.NonNull
    private   String surname;

    @Indexed( name  =  "user_email_index" , direction  =  IndexDirection. DESCENDING,unique = true)
    @com.mongodb.lang.NonNull
    private String email;



    @DBRef(lazy = true)
    private Set<ConferencesForUser> conferenc_id=new HashSet();


    @Override
    public List<GrantedAuthority> getAuthorities() {
       // return authorities;
      /*  List<String>roles=new ArrayList<>();
        for(Roles r:authorities) {
            roles.add(r.getRolename());
        }*/
       return authorities.stream()
                .map(role->new SimpleGrantedAuthority(role.getRolename())).collect(Collectors.toList());
    }

    public String getRolenames(){
        for (Roles r:authorities){
            return r.getRolename();
        }
        return "ANONIMUS";
    }

}
