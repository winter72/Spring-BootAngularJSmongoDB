package conference.itstep.conference.interfaces_models;

import conference.itstep.conference.models_entitys.ConferencesForUser;
import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//@RepositoryRestResource(collectionResourceRel = "conferencesuser", path = "conferencesuser")
public interface ConferenceRepository extends MongoRepository<ConferencesForUser,String> {
}
