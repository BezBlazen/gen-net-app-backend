package bzblz.gen_net_app.services;

import bzblz.gen_net_app.exceptions.AlreadyExistsException;
import bzblz.gen_net_app.exceptions.AppException;
import bzblz.gen_net_app.model.Account;
import bzblz.gen_net_app.model.AccountRole;
import bzblz.gen_net_app.model.Project;
import bzblz.gen_net_app.repositories.ProjectRepository;
import jakarta.persistence.EntityManager;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public Optional<Project> findById(Long id) {
        return projectRepository.findById(id);
    }
    public List<Project> findAllByAccount(Account account) {
        if (account == null) {
            return new ArrayList<>();
        }
        return projectRepository.findAllByAccount(account);
    }
    @Transactional
    public Project add(Account account, String title) throws AppException {
        if (account.getRole() == AccountRole.ROLE_SESSION && !projectRepository.findAllByAccount(account).isEmpty()) {
            throw new AppException("Log in to create a new project");
        }
        final Project project = new Project(title, account);
        return projectRepository.save(project);
    }
    @Transactional
    public Project add(Project project) throws AlreadyExistsException {
        if (findById(project.getId()).isPresent())
            throw new AlreadyExistsException(String.format("Project '%s' already exist", project));

        return projectRepository.save(project);
    }
    @Transactional
    public Project save(Project project) {
        return projectRepository.save(project);
    }
    @Transactional
    public void delete(Integer projectId, Account account) throws NotFoundException {
        final Optional<Project> project = projectRepository.findProjectByIdAndAccount(projectId, account);
        if (project.isEmpty())
            throw new NotFoundException(String.format("Project '%s' not found", projectId));
        projectRepository.delete(project.get());
    }
    @Transactional
    public void deleteAll(Account account) {
        projectRepository.deleteAll(findAllByAccount(account));
    }
    @Transactional
    public void moveAll(Account from, Account to) {
        final List<Project> projectList = findAllByAccount(from);
        for (Project project : projectList) {
            project.setAccount(to);
            save(project);
        }
    }
}
