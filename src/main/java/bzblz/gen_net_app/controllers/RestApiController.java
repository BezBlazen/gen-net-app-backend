package bzblz.gen_net_app.controllers;

import bzblz.gen_net_app.dto.AccountDto;
import bzblz.gen_net_app.exceptions.AlreadyExistsException;
import bzblz.gen_net_app.model.Account;
import bzblz.gen_net_app.model.Person;
import bzblz.gen_net_app.model.Project;
import bzblz.gen_net_app.security.AccountsDetails;
import bzblz.gen_net_app.services.PersonsService;
import bzblz.gen_net_app.services.ProjectsService;
import bzblz.gen_net_app.services.AccountsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3003", allowCredentials = "true")
public class RestApiController {
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;
    private final PersonsService personsService;
    private final ProjectsService projectsService;
    private final AccountsService accountsService;

    @GetMapping("/hi")
    public String hi() {
        return "hi!";
    }

    @GetMapping(path = "/auth/profile")
    public AccountDto getUserProfile(HttpServletResponse response) {
        System.out.println("SessionId: " + RequestContextHolder.currentRequestAttributes().getSessionId());

        AccountDto accountDto = null;
        SecurityContext context = SecurityContextHolder.getContext();
        System.out.println("getName: " + context.getAuthentication().getName());
//        Cookie cookie = new Cookie("USERNAME", null);
        if (!(context.getAuthentication() instanceof AnonymousAuthenticationToken)) {
            accountDto = new AccountDto(((AccountsDetails)context.getAuthentication().getPrincipal()).getAccount());

//            cookie.setValue(userDto.getUsername());
        } else {
//            userDto = new UserDto();
//            userDto.setUsername(RequestContextHolder.currentRequestAttributes().getSessionId());
//            userDto.setUserRole(UserRole.ROLE_SESSION_USER);
//            cookie.setMaxAge(0);
        }
//        response.addCookie(cookie);
        return accountDto;
    }

    @GetMapping("/persons")
    public List<Person> getPersonList(HttpSession session, @RequestParam(name="schema_id", required = false) Integer schemaId) {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context.getAuthentication() instanceof AnonymousAuthenticationToken) {
            System.out.println("sessionId: " + session.getId());
            // TODO Before create schema for anonymous user
            return new ArrayList<>();

        } else {
            final Project project = projectsService.findById(schemaId).orElse(null);
            if (project != null && project.getAccount().getId() == ((AccountsDetails)context.getAuthentication().getPrincipal()).getAccount().getId()) {
                return personsService.findByProjectId(schemaId);
            } else {
                // TODO Schema is not belong user
                return new ArrayList<>();
            }
        }

//        return new ArrayList<>();
    }
    @GetMapping("/schemas")
    public List<Project> getSchemaList() {
        final SecurityContext context = SecurityContextHolder.getContext();
        if (context.getAuthentication() instanceof AnonymousAuthenticationToken) {
            System.out.println("AnonymousAuthenticationToken");
            return null;
        } else {
            return projectsService.findAllByAccountIs(((AccountsDetails)context.getAuthentication().getPrincipal()).getAccount());
        }
    }
    @PostMapping(path = "/persons", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPerson(@RequestBody Person person, @CookieValue("APPSESSION") String cookie) {
        System.out.println("APPSESSION: " + cookie);
//        person.setSchema().setSchema_id(1);
        personsService.add(person);
//        return new ResponseEntity<>()
    }

    @PostMapping(path = "/auth/signin", consumes = "application/json")
    public AccountDto authSignIn(@RequestBody Account account, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("User: " + account.toString());
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(
                account.getUsername(), account.getPassword());
        System.out.println("token: " + token.toString());
        System.out.println("authenticationManager: " + String.valueOf(authenticationManager));
        Authentication authentication = null;
        AuthenticationException authException = null;
        SecurityContext context = SecurityContextHolder.getContext();
        try {
            authentication = authenticationManager.authenticate(token);
            System.out.println("authentication: " + String.valueOf(authentication));
        } catch (AuthenticationException e) {
            authException = e;
        }
        System.out.println("is AnonymousAuthenticationToken : " + String.valueOf(context.getAuthentication() instanceof AnonymousAuthenticationToken));
        System.out.println("isAuthenticated: " + String.valueOf(context.getAuthentication().isAuthenticated()));
        if (authException != null) {
            System.out.println("Auth error: " + authException.getMessage());
        } else {
            context.setAuthentication(authentication);

            System.out.println("UDetails: " + String.valueOf((AccountsDetails)context.getAuthentication().getPrincipal()));
            SecurityContextHolder.setContext(context);
            securityContextRepository.saveContext(context, request, response);
            return new AccountDto(((AccountsDetails)context.getAuthentication().getPrincipal()).getAccount());
        }
        return null;
    }
    @PostMapping(path = "/auth/signup", consumes = "application/json")
    public AccountDto authSignUp(@RequestBody Account account, HttpServletRequest request, HttpServletResponse response) {
        try {
            accountsService.add(account);
            return new AccountDto(account);
        } catch (AlreadyExistsException e) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
        return null;
    }
}
