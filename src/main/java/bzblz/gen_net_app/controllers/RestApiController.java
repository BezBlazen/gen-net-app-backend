package bzblz.gen_net_app.controllers;

import bzblz.gen_net_app.dto.UserDto;
import bzblz.gen_net_app.exceptions.AlreadyExistsException;
import bzblz.gen_net_app.model.Person;
import bzblz.gen_net_app.model.User;
import bzblz.gen_net_app.model.UserRole;
import bzblz.gen_net_app.security.UDetails;
import bzblz.gen_net_app.services.PersonsService;
import bzblz.gen_net_app.services.UService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3003", allowCredentials = "true")
public class RestApiController {
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;
    private final PersonsService personsService;
    private final UService uService;

    @GetMapping("/hi")
    public String hi() {
        return "hi!";
    }

    @GetMapping(path = "/auth/profile")
    public UserDto getUserProfile(HttpServletResponse response) {
        System.out.println("SessionId: " + RequestContextHolder.currentRequestAttributes().getSessionId());

        UserDto userDto = null;
        SecurityContext context = SecurityContextHolder.getContext();
        System.out.println("getName: " + context.getAuthentication().getName());
//        Cookie cookie = new Cookie("USERNAME", null);
        if (!(context.getAuthentication() instanceof AnonymousAuthenticationToken)) {
            userDto = new UserDto(((UDetails)context.getAuthentication().getPrincipal()).getUser());

//            cookie.setValue(userDto.getUsername());
        } else {
//            userDto = new UserDto();
//            userDto.setUsername(RequestContextHolder.currentRequestAttributes().getSessionId());
//            userDto.setUserRole(UserRole.ROLE_SESSION_USER);
//            cookie.setMaxAge(0);
        }
//        response.addCookie(cookie);
        return userDto;
    }

    @GetMapping("/persons")
    public List<Person> getPersonList(@RequestParam Map<String, String> req) {
////        System.out.println(req);
//        SecurityContext context = SecurityContextHolder.getContext();
//        System.out.println("isAnonymousAuthenticationToken : " + String.valueOf(context.getAuthentication() instanceof AnonymousAuthenticationToken));
//        if (context.getAuthentication() instanceof AnonymousAuthenticationToken) {
//        } else {
//            System.out.println("UserName: " + ((UDetails)context.getAuthentication().getPrincipal()).getUsername());
//        }
        return personsService.findAll();
    }
    @PostMapping(path = "/persons", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPerson(@RequestBody Person person, @CookieValue("APPSESSION") String cookie) {
        System.out.println("APPSESSION: " + cookie);
        person.setSchema_id(1);
        personsService.add(person);
//        return new ResponseEntity<>()
    }

    @PostMapping(path = "/auth/signin", consumes = "application/json")
    public UserDto authSignIn(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("User: " + user.toString());
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(
                user.getUsername(), user.getPassword());
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

            System.out.println("UDetails: " + String.valueOf((UDetails)context.getAuthentication().getPrincipal()));
            SecurityContextHolder.setContext(context);
            securityContextRepository.saveContext(context, request, response);
            return new UserDto(((UDetails)context.getAuthentication().getPrincipal()).getUser());
        }
        return null;
    }
    @PostMapping(path = "/auth/signup", consumes = "application/json")
    public UserDto authSignUp(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
        try {
            uService.add(user);
            return new UserDto(user);
        } catch (AlreadyExistsException e) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
        return null;
    }
}
