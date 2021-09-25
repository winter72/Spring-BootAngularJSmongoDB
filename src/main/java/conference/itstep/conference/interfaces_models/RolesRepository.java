package conference.itstep.conference.interfaces_models;

import conference.itstep.conference.models_entitys.Roles;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


//@RepositoryRestResource(collectionResourceRel = "rolesuser", path = "rolesuser")

public interface RolesRepository extends MongoRepository<Roles,String> {

    Roles findRolesByRolename(String r);
}
