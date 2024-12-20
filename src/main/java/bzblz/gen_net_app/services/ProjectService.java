package bzblz.gen_net_app.services;

import bzblz.gen_net_app.exceptions.AlreadyExistsException;
import bzblz.gen_net_app.model.Account;
import bzblz.gen_net_app.model.Project;
import bzblz.gen_net_app.repositories.ProjectRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final EntityManager entityManager;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, EntityManager entityManager) {
        this.projectRepository = projectRepository;
        this.entityManager = entityManager;
    }

    public List<Project> findAllByAccountIs(Account account) {
        return projectRepository.findAllByAccountIs(account);
    }
    public Optional<Project> findById(Long id) {
        return projectRepository.findById(id);
    }
    public List<Project> findAllByAccount(Account account) {
        return projectRepository.findAllByAccount(account);
    }
    @Transactional
    public Project add(Account account, String title) {
        final Project project = new Project(title, account);
        return projectRepository.save(project);
    }
    @Transactional
    public Project add(Project project) throws AlreadyExistsException {
        if (findById(project.getId()).isPresent())
            throw new AlreadyExistsException(String.format("Project '%s' already exist", project));

        return projectRepository.save(project);
    }
}
