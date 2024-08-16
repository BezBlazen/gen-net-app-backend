package bzblz.gen_net_app.services;

import bzblz.gen_net_app.exceptions.AlreadyExistsException;
import bzblz.gen_net_app.model.Account;
import bzblz.gen_net_app.model.Project;
import bzblz.gen_net_app.repositories.ProjectsRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProjectsService {
    private final ProjectsRepository projectsRepository;
    private final EntityManager entityManager;

    @Autowired
    public ProjectsService(ProjectsRepository projectsRepository, EntityManager entityManager) {
        this.projectsRepository = projectsRepository;
        this.entityManager = entityManager;
    }

    public List<Project> findAllByAccountIs(Account account) {
        return projectsRepository.findAllByAccountIs(account);
    }
    public Optional<Project> findById(Integer id) {
        return projectsRepository.findById(id);
    }
    public List<Project> findAllByAccount(Account account) {
        return projectsRepository.findAllByAccount(account);
    }
    @Transactional
    public Project add(Account account, String title) {
        final Project project = new Project(title, account);
        return projectsRepository.save(project);
    }
    @Transactional
    public Project add(Project project) throws AlreadyExistsException {
        if (findById(project.getId()).isPresent())
            throw new AlreadyExistsException(String.format("Project '%s' already exist", project));

        return projectsRepository.save(project);
    }
}
