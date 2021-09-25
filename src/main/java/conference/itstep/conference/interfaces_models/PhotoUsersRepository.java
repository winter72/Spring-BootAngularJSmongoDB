package conference.itstep.conference.interfaces_models;

import conference.itstep.conference.models_entitys.Photo_User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PhotoUsersRepository extends MongoRepository<Photo_User,String> {
    Photo_User findByTitle(String title);
}
