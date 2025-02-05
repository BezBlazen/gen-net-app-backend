package bzblz.gen_net_app.repositories;

import bzblz.gen_net_app.model.Project;
import bzblz.gen_net_app.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByAccountIs(Account account);
    List<Project> findAllByAccount(Account account);
    Optional<Project> findProjectByIdAndAccount(Integer projectId, Account account);
}
