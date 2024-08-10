package bzblz.gen_net_app.repositories;

import bzblz.gen_net_app.model.Project;
import bzblz.gen_net_app.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectsRepository extends JpaRepository<Project, Integer> {
    List<Project> findAllByAccountIs(Account account);
}
