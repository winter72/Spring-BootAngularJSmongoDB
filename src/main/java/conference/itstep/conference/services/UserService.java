package conference.itstep.conference.services;

import conference.itstep.conference.config.UserAuthefication;
import conference.itstep.conference.interfaces_models.UsersRepository;
import conference.itstep.conference.models_entitys.Users;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(@NonNull String s) throws UsernameNotFoundException {
        try {
            Users user = usersRepository.findByEmail(s);
            if(user==null){
                throw new UsernameNotFoundException(s+"not found");
            }

           return user;
         /*   return new Users(user.getId(),user.getAuthorities(),user.getUsername(),user.getPassword(),user.isAccountNonExpired(),
                    user.isAccountNonLocked(),user.isCredentialsNonExpired(),user.isEnabled(),user.getSurname(),user.getEmail(),user.getConferenc_id());*/

        }catch (Exception e) {
            throw new UsernameNotFoundException("User not found!");
        }
      //.orElseThrow(() -> new UsernameNotFoundException("Username: " + s + " not found"));
    }

    public Optional<Users> findByID(@NonNull String o) {
        return usersRepository.findById(o);
    }
}
