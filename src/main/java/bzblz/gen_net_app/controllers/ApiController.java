package bzblz.gen_net_app.controllers;

import bzblz.gen_net_app.dto.AccountDto;
import bzblz.gen_net_app.exceptions.AlreadyExistsException;
import bzblz.gen_net_app.exceptions.AppException;
import bzblz.gen_net_app.exceptions.UnexpectedRequestException;
import bzblz.gen_net_app.model.Account;
import bzblz.gen_net_app.model.AccountRole;
import bzblz.gen_net_app.model.Person;
import bzblz.gen_net_app.model.Project;
import bzblz.gen_net_app.security.AccountDetails;
import bzblz.gen_net_app.services.PersonService;
import bzblz.gen_net_app.services.AccountService;
import bzblz.gen_net_app.services.ProjectService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class ApiController {
    private final AuthenticationManager authenticationManager;
    private final PersonService personService;
    private final ProjectService projectService;
    private final AccountService accountService;
    private final AuthenticationController authenticationController;

    private Optional<Account> getCurrentAccount() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context.getAuthentication() instanceof AnonymousAuthenticationToken) {
            final String sessionAccountName = StringUtils.isNotBlank(RequestContextHolder.currentRequestAttributes().getSessionId()) ? "_" + RequestContextHolder.currentRequestAttributes().getSessionId() : null;
            return accountService.findByUsername(sessionAccountName);
        } else {
            return Optional.of(((AccountDetails)context.getAuthentication().getPrincipal()).getAccount());
        }
    }
    private Optional<UUID> getCurrentAccountId() {
        return getCurrentAccount().map(Account::getId);
    }
    private String getCurrentAccountUsername() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context.getAuthentication() instanceof AnonymousAuthenticationToken) {
            return StringUtils.isNotBlank(RequestContextHolder.currentRequestAttributes().getSessionId()) ? "_" + RequestContextHolder.currentRequestAttributes().getSessionId() : null;
        } else {
            return ((AccountDetails)context.getAuthentication().getPrincipal()).getAccount().getUsername();
        }
    }

    @GetMapping(path = "/auth/profile")
    public AccountDto getUserProfile() {
        return new AccountDto(accountService.findByUsername(getCurrentAccountUsername()).orElse(null));
    }
    //---------------------------------------
    // Person
    // get
    @GetMapping("/persons")
    public List<Person> getPersonList(HttpSession session, @RequestParam(name="project_id", required = false) UUID projectId) {
        System.out.println("getPersonList");
        SecurityContext context = SecurityContextHolder.getContext();
        if (context.getAuthentication() instanceof AnonymousAuthenticationToken) {
            // TODO Before create schema for anonymous user
            return new ArrayList<>();

        } else {
            final Project project = projectService.findById(projectId).orElse(null);
            if (project != null && project.getAccountId().equals(((AccountDetails)context.getAuthentication().getPrincipal()).getAccount().getId())) {
                return personService.findByProjectId(projectId);
            } else {
                // TODO Schema is not belong user
                return new ArrayList<>();
            }
        }
    }
    @GetMapping("/persons/{personId}")
    public Person getPerson(@PathVariable UUID personId) throws UnexpectedRequestException, NotFoundException {
        System.out.println("getPerson");
        final Person person = personService.findOne(personId);
        checkProject(person.getProjectId());
        return person;
    }
    // post
    @PostMapping(path = "/persons", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Person addPerson(@RequestBody Person person) throws  UnexpectedRequestException {
        checkProject(person.getProjectId());
        return personService.add(person);
    }
    // put
    @PutMapping(path = "/persons", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Person putPerson(@RequestBody Person person) throws UnexpectedRequestException {
        checkProject(person.getProjectId());
        return personService.save(person);
    }
    // delete
    @DeleteMapping(path = "/persons/{personId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerson(@PathVariable UUID personId) throws UnexpectedRequestException, NotFoundException {
        checkProject(personService.findByProjectId(personId).get(0).getProjectId());
        personService.delete(personId);
    }
    // Person
    //---------------------------------------
    // Project
    // get
    @GetMapping("/projects")
    public List<Project> getProjectList() {
        return projectService.findAllByAccountId(getCurrentAccountId().orElse(null));
    }

    // post
    @PostMapping(path = "/projects", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Project postProject(@RequestBody Project project, HttpServletRequest request,
                               HttpServletResponse response) throws AppException, UnexpectedRequestException, AlreadyExistsException {
        final Optional<Account> optionalAccount = getCurrentAccount();
        final Account account = optionalAccount.isPresent() ? optionalAccount.get() : authenticationController.newSession(request, response);
        if (account.getRole() == AccountRole.ROLE_SESSION && !projectService.findAllByAccountId(account.getId()).isEmpty())
            throw new AppException("Log in to create a more projects");
        return projectService.add(account.getId(), project.getTitle());
    }

    // put
    @PutMapping(path = "/projects", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Project putProject(@RequestBody Project project, HttpServletRequest request,
                              HttpServletResponse response) {
        final Account account = getCurrentAccount().orElseThrow();
        project.setAccountId(account.getId());
        return projectService.save(project);
    }
    // delete
    @DeleteMapping(path = "/projects/{projectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@PathVariable UUID projectId) throws NotFoundException {
        projectService.delete(projectId, getCurrentAccountId().orElseThrow());
    }
    // get tmp projects
    @GetMapping("/projects/tmp")
    public List<Project> getTmpProjectList(HttpServletRequest request) {
        Account sessionAccount = accountService.findByUsername("~" + request.getSession().getId()).orElse(null);
        return sessionAccount == null ? new ArrayList<>() : projectService.findAllByAccountId(sessionAccount.getId());
    }
    // put tmp projects
    @PutMapping("/projects/tmp")
    @ResponseStatus(HttpStatus.CREATED)
    public void putTmpProjectList(HttpServletRequest request) {
        Account sessionAccount = accountService.findByUsername("~" + request.getSession().getId()).orElse(null);
        if (sessionAccount != null)
            projectService.moveAll(sessionAccount.getId(), getCurrentAccountId().orElseThrow());
    }
    // delete tmp projects
    @DeleteMapping("/projects/tmp")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTmpProjectList(HttpServletRequest request) {
        Account sessionAccount = accountService.findByUsername("~" + request.getSession().getId()).orElse(null);
        if (sessionAccount != null)
            projectService.deleteAll(sessionAccount.getId());
    }
    // check project
    public void checkProject(UUID projectId) throws UnexpectedRequestException {
        final Optional<Account> optionalAccount = getCurrentAccount();
        final Optional<Project> optionalProject = projectService.findById(projectId);
        if (optionalAccount.isEmpty() || optionalProject.isEmpty() || !optionalProject.get().getAccountId().equals(optionalAccount.get().getId())) {
            throw new UnexpectedRequestException("Project not found");
        }
    }
    // Project
    //---------------------------------------

}
