package bzblz.gen_net_app.services;

import bzblz.gen_net_app.model.Account;
import bzblz.gen_net_app.model.Project;
import bzblz.gen_net_app.repositories.ProjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProjectsService {
    private final ProjectsRepository projectsRepository;

    @Autowired
    public ProjectsService(ProjectsRepository projectsRepository) {
        this.projectsRepository = projectsRepository;
    }

    public List<Project> findAllByAccountIs(Account account) {
        return projectsRepository.findAllByAccountIs(account);
    }
    public Optional<Project> findById(Integer id) {
        return projectsRepository.findById(id);
    }
}
