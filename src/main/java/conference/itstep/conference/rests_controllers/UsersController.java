package conference.itstep.conference.rests_controllers;


import conference.itstep.conference.components.TokenHandler;
import conference.itstep.conference.interfaces_models.RolesRepository;
import conference.itstep.conference.interfaces_models.UsersRepository;
import conference.itstep.conference.models_dto.AuthenticationUserDto;
import conference.itstep.conference.models_entitys.Roles;
import conference.itstep.conference.models_entitys.Users;
import conference.itstep.conference.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1",produces = MediaType.APPLICATION_JSON_VALUE)
public class UsersController {

        @Autowired
        private UsersRepository usersRepository;
        @Autowired
        private RolesRepository rolesRepository;
        @Autowired
        PhotoService photoService;
        @Autowired
        PasswordEncoder passwordEncoder;

   @Autowired
    TokenHandler tokenHandler;
@Autowired
    AuthenticationManager authenticationManager;

@Autowired
    UserDetailsService userDetails;

    @RequestMapping(value = "/peoples",method = RequestMethod.GET)//,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Users>> retAllUsers() {

        List<Users>allUsers= usersRepository.findAll();
        if(!allUsers.isEmpty())
            return new ResponseEntity<>(allUsers,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @RequestMapping(value = "/one_user",method = RequestMethod.POST)
    public  ResponseEntity <Users>createUser(@RequestBody @Valid Users user){

       // if(!user.getUsername().isEmpty()) {
        try{
            if(!user.getUsername().isEmpty()) {
                Roles role = rolesRepository.findRolesByRolename("ROLE_USER");
                List<Roles>r1=new ArrayList<>();
                r1.add(role);
                user=Users.builder()
                        .username(user.getUsername())
                        .surname(user.getSurname())
                        .email(user.getEmail())
                        .password(passwordEncoder.encode(user.getPassword()))
                        .accountNonExpired(true)
                        .authorities(r1)
                        .accountNonLocked(true)
                        .credentialsNonExpired(true)
                        .enabled(true)
                        .build();
                usersRepository.save(user);
                return new ResponseEntity<>(user, HttpStatus.CREATED);
            }else
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (NullPointerException ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/check_one",method = RequestMethod.POST)
    public  ResponseEntity <AuthenticationUserDto> checkUser(@RequestBody @Valid AuthenticationUserDto user) {
        Users user1 = usersRepository.findByEmail(user.getEmail());
        try {
            if (user1 != null && passwordEncoder.matches(user.getPassword(), usersRepository.findByEmail(user1.getEmail()).getPassword())) {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user1.getEmail(), user.getPassword(), user1.getAuthorities()));
                String token = tokenHandler.createToken(user1);
                String jsonInString=photoService.retEncodeStr(user1.getId());
                return new ResponseEntity<>(AuthenticationUserDto.builder()
                        .id(user1.getId())
                        .firstname(user1.getUsername())
                        .surname(user1.getSurname())
                        .email(user1.getEmail())
                        .role(user1.getRolenames())
                        .jwttoken(token)
                        .photo_user(jsonInString)
                        .build(), HttpStatus.OK);
            } else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (NullPointerException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping(value = "/update_user",method = RequestMethod.PUT)
    public  ResponseEntity <AuthenticationUserDto>updateUser(@RequestBody @Valid AuthenticationUserDto user ){
       Optional<Users> optionalUser=usersRepository.findById(user.getId());
       // Users usr = optionalUser.isPresent() ? optionalUser.get() : new Users();
        Users usr =optionalUser.orElseThrow(()->new RuntimeException() );
            if (passwordEncoder.matches(user.getOldpassword(),usr.getPassword() )) {
                usr.setUsername(user.getFirstname());
                usr.setSurname(user.getSurname());
                System.out.println(user.getEmail().equals(usr.getEmail()));
                if(!user.getEmail().equalsIgnoreCase(usr.getEmail())){
                    //usr.setEmail(user.getEmail());
                   System.out.println(user.getEmail()+"---"+usr.getEmail());
                }
                try {
                    if (!user.getPassword().isEmpty()) {
                        usr.setPassword(passwordEncoder.encode(user.getPassword()));
                    }
                }catch (NullPointerException ex){

                }
                usersRepository.save(usr);
                AuthenticationUserDto u=user;
                u.setJwttoken(tokenHandler.createToken(usr));
                u.setPhoto_user(photoService.retEncodeStr(usr.getId()));
                return new ResponseEntity<>(u, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

    }

   // @RequestMapping(value = "/delete_user",method = RequestMethod.DELETE)
//@RequestPart("file") MultipartFile file
    @RequestMapping(value="/user_file",method = RequestMethod.POST)
    public ResponseEntity<?> updateFoto( @RequestPart("file") MultipartFile file,@RequestParam("userid") String id) throws IOException {
        try {
            if (!StringUtils.isEmpty(file.getOriginalFilename())) {
                photoService.saveOrUpdatePhotoUser(id, file, file.getContentType());
              String foto=photoService.retEncodeStr(id);
                return new ResponseEntity<>(foto,HttpStatus.OK);
            }
        }catch (NullPointerException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
