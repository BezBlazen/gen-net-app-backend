package bzblz.gen_net_app.repositories;

import bzblz.gen_net_app.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    public List<Person> findByProjectId(Long id);
}
