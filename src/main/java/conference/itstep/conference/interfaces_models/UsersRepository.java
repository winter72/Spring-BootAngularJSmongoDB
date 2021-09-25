package conference.itstep.conference.interfaces_models;

import conference.itstep.conference.models_entitys.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

//@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UsersRepository extends MongoRepository<Users,String> {

    List<Users>findByUsername(@Param("username") String name);

   Users findByEmailAndPassword(String em,String ps);

   Users findByEmail(@Param("email")String em);
}
