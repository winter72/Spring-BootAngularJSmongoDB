package conference.itstep.conference.services;

import conference.itstep.conference.interfaces_models.PhotoUsersRepository;
import conference.itstep.conference.models_entitys.Photo_User;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
@Component
public class PhotoService {
    @Autowired
    private  PhotoUsersRepository photoUserRepository;


    private  boolean checkPhotoUser(String id){
        Photo_User photo_user = photoUserRepository.findByTitle(id);
        try {
            if (!StringUtils.isEmpty(photo_user.getTitle()))
                return true;
        }catch (NullPointerException ex) {
            return false;
        }
        return false;
    }

    private Photo_User backPhoto(String id){
        return photoUserRepository.findByTitle(id);
    }

    public void saveOrUpdatePhotoUser(String id, MultipartFile file,String f_tp)  {
        Photo_User photo_user;
        try {
                if(!StringUtils.isEmpty(photoUserRepository.findByTitle(id).getTitle())){
                    photo_user=photoUserRepository.findByTitle(id);

                    photo_user.setType_image(file.getContentType());
                    try {
                        photo_user.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
                    } catch (IOException e) {
                        e.getMessage();
                    }
                    photoUserRepository.save(photo_user);
                }


        }catch (NullPointerException ex){
            try {
                photo_user = Photo_User.builder()
                        .title(id)
                        .type_image(file.getContentType())
                        .image(new Binary(BsonBinarySubType.BINARY, file.getBytes()))
                        .build();
                photoUserRepository.save(photo_user);
            } catch (IOException e) {
                e.getMessage();
            }

        }
    }

    public String retEncodeStr(String id){
        String mainString="";
        if(checkPhotoUser(id)) {
           Photo_User photoUser = backPhoto(id);
           byte[] fileContent = photoUser.getImage().getData();
           String encodedString = Base64.getEncoder().encodeToString(fileContent);
           mainString = "data:" + photoUser.getType_image() + ";base64," + encodedString;
       }else{
            mainString="/img/people.png";
        }
             return mainString;
    }
}
