package bzblz.gen_net_app.services;

import bzblz.gen_net_app.exceptions.AlreadyExistsException;
import bzblz.gen_net_app.exceptions.AppException;
import bzblz.gen_net_app.model.Account;
import bzblz.gen_net_app.model.AccountRole;
import bzblz.gen_net_app.model.Project;
import bzblz.gen_net_app.repositories.ProjectRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ProjectService {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }
    public Optional<Project> findById(UUID id) {
        return projectRepository.findById(id);
    }
    public List<Project> findAllByAccountId(UUID accountId) {
        if (accountId == null) {
            return new ArrayList<>();
        }
        return projectRepository.findAllByAccountId(accountId);
    }
    @Transactional
    public Project add(UUID accountId, String title) throws AppException {
        final Project project = new Project(title, accountId);
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
    public void delete(UUID projectId, UUID accountId) throws NotFoundException {
        final Optional<Project> project = projectRepository.findProjectByIdAndAccountId(projectId, accountId);
        if (project.isEmpty())
            throw new NotFoundException(String.format("Project '%s' not found", projectId));
        projectRepository.delete(project.get());
    }
    @Transactional
    public void deleteAll(UUID accountId) {
        projectRepository.deleteAll(findAllByAccountId(accountId));
    }
    @Transactional
    public void moveAll(UUID fromAccountId, UUID toAccountId) {
        final List<Project> projectList = findAllByAccountId(fromAccountId);
        for (Project project : projectList) {
            project.setAccountId(toAccountId);
            save(project);
        }
    }
}
