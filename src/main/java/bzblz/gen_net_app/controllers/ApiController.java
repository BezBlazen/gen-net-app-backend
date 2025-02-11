package bzblz.gen_net_app.controllers;

import bzblz.gen_net_app.dto.AccountDto;
import bzblz.gen_net_app.exceptions.AlreadyExistsException;
import bzblz.gen_net_app.exceptions.AppException;
import bzblz.gen_net_app.exceptions.UnexpectedRequestException;
import bzblz.gen_net_app.model.Account;
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
    private String getCurrentAccountUsername() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context.getAuthentication() instanceof AnonymousAuthenticationToken) {
            return StringUtils.isNotBlank(RequestContextHolder.currentRequestAttributes().getSessionId()) ? "_" + RequestContextHolder.currentRequestAttributes().getSessionId() : null;
        } else {
            return ((AccountDetails)context.getAuthentication().getPrincipal()).getAccount().getUsername();
        }
    }

    @GetMapping("/hi")
    public String hi() {
        return "hi!";
    }

    @GetMapping(path = "/auth/profile")
    public AccountDto getUserProfile() {
        return new AccountDto(accountService.findByUsername(getCurrentAccountUsername()).orElse(null));
    }
    //---------------------------------------
    // Person
    // get
    @GetMapping("/persons")
    public List<Person> getPersonList(HttpSession session, @RequestParam(name="schema_id", required = false) Long schemaId) {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context.getAuthentication() instanceof AnonymousAuthenticationToken) {
            System.out.println("sessionId: " + session.getId());
            // TODO Before create schema for anonymous user
            return new ArrayList<>();

        } else {
            final Project project = projectService.findById(schemaId).orElse(null);
            if (project != null && project.getAccount().getId() == ((AccountDetails)context.getAuthentication().getPrincipal()).getAccount().getId()) {
                return personService.findByProjectId(schemaId);
            } else {
                // TODO Schema is not belong user
                return new ArrayList<>();
            }
        }

    }

    // post
    @PostMapping(path = "/persons", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPerson(@RequestBody Person person, @CookieValue("APPSESSION") String cookie) {
        System.out.println("APPSESSION: " + cookie);
        personService.add(person);
    }
    // Person
    //---------------------------------------
    // Project
    // get
    @GetMapping("/projects")
    public List<Project> getProjectList() {
            return projectService.findAllByAccount(getCurrentAccount().orElse(null));
    }

    // post
    @PostMapping(path = "/projects", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Project postProject(@RequestBody Project project, HttpServletRequest request,
                               HttpServletResponse response) throws AppException, UnexpectedRequestException, AlreadyExistsException {
        final Optional<Account> optionalAccount = getCurrentAccount();
        final Account account = optionalAccount.isPresent() ? optionalAccount.get() : authenticationController.newSession(request, response);
        return projectService.add(account, project.getTitle());
    }

    // put
    @PutMapping(path = "/projects", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Project putProject(@RequestBody Project project, HttpServletRequest request,
                              HttpServletResponse response) {
        final Account account = getCurrentAccount().orElseThrow();
        project.setAccount(account);
        return projectService.save(project);
    }
    // delete
    @DeleteMapping(path = "/projects/{projectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@PathVariable Integer projectId) throws NotFoundException {
        projectService.delete(projectId, getCurrentAccount().orElseThrow());
    }
    // Project
    //---------------------------------------

}
