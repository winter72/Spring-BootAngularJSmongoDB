package conference.itstep.conference.config;

import conference.itstep.conference.models_entitys.Users;
import lombok.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class UserAuthefication implements Authentication {

    private final Users user;
    private boolean authenticated=true;

    public UserAuthefication(@NonNull Users user1) {

        this.user = user1;
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        return user.getAuthorities();/*.stream()
                .map(role->new SimpleGrantedAuthority(role.getRolename())).collect(Collectors.toList());*/
    }

    @Override
    public Object getCredentials() {
        return user.getPassword();
    }

    @Override
    public Object getDetails() {
        return user;
    }

    @Override
    public Object getPrincipal() {
        return user;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        authenticated=b;
    }

    @Override
    public String getName() {
        return user.getUsername();
    }
}
