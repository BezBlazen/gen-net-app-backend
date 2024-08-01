package bzblz.gen_net_app.repositories;

import bzblz.gen_net_app.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonsRepository extends JpaRepository<Person, Integer> {

}
