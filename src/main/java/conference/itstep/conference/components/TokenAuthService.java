package conference.itstep.conference.components;

import conference.itstep.conference.config.UserAuthefication;
import conference.itstep.conference.services.UserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
public class TokenAuthService {
    @Autowired
    UserService userService;
    @Autowired
    TokenHandler tokenHandler;

    private static String AUTH_HEADER_NAME="Authorization";

    public Optional<Authentication> getAuthentication(@NonNull HttpServletRequest request) {

     // System.out.println("--------"+tokenHandler.extractUserId(request.getHeader(AUTH_HEADER_NAME)));

        return Optional
                .ofNullable(request.getHeader(AUTH_HEADER_NAME))
                .flatMap(tokenHandler::extractUserId)
                .flatMap(userService::findByID)
                .map(UserAuthefication::new);
    }

    public String resolveToken(@NonNull HttpServletRequest request){
        String bearerToken=request.getHeader(AUTH_HEADER_NAME);
        if(!bearerToken.isEmpty()){
            //&& bearerToken.startsWith("Bearer"))
            return bearerToken;

           // return bearerToken.substring(7,bearerToken.length());
        }
        return null;
    }
}
