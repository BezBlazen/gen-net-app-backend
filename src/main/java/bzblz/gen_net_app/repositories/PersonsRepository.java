package bzblz.gen_net_app.repositories;

import bzblz.gen_net_app.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonsRepository extends JpaRepository<Person, Integer> {
    public List<Person> findByProjectId(Integer id);
//    public List<Person> findBySchema_idIn(Collection<Integer> ids);

}
