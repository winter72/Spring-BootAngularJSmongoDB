package conference.itstep.conference.models_dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationUserDto {

    private String id;
    private String role;
    private String firstname;
    private String surname;
    private String email;
    private String password;
    private String repeatpassword;
    private String oldpassword;
    private String jwttoken;
    private String status;
    private String photo_user;
}
