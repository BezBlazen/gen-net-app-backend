package bzblz.gen_net_app.repositories;

import bzblz.gen_net_app.model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectRepository extends MongoRepository<Project, UUID> {
//    List<Project> findAllByAccountIs(Account account);
    List<Project> findAllByAccountId(UUID accountId);
    Optional<Project> findProjectByIdAndAccountId(UUID projectId, UUID accountId);
}
