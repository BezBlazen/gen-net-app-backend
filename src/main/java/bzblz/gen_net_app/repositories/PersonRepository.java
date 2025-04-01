package bzblz.gen_net_app.repositories;

import bzblz.gen_net_app.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PersonRepository extends MongoRepository<Person, UUID> {
    public List<Person> findByProjectId(UUID id);
}
