package bzblz.gen_net_app.controllers;

import bzblz.gen_net_app.dto.AccountDto;
import bzblz.gen_net_app.dto.AccountSignInDto;
import bzblz.gen_net_app.dto.AccountSignUpDto;
import bzblz.gen_net_app.exceptions.AlreadyExistsException;
import bzblz.gen_net_app.exceptions.AppException;
import bzblz.gen_net_app.exceptions.UnexpectedRequestException;
import bzblz.gen_net_app.model.Account;
import bzblz.gen_net_app.services.AccountService;
import bzblz.gen_net_app.services.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class AuthenticationController {
    private final UserDetailsService userDetailsService;
    private final AccountService accountService;
    private final AuthenticationService authenticationService;
    private final SecurityContextRepository securityContextRepository;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private final SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    @PostMapping("/sign_up")
    public ResponseEntity<String> signUp(@RequestBody AccountSignUpDto accountSignUpDto, HttpServletResponse response) throws AlreadyExistsException {
        System.out.println("signUp");
        accountService.addAccountWithRoleUser(new Account(accountSignUpDto.getUsername(), accountSignUpDto.getPassword()));
        Cookie cookie = new Cookie("platform","mobile");

        // expires in 7 days
        cookie.setMaxAge(7 * 24 * 60 * 60);

        // optional properties
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        // add cookie to response
        response.addCookie(cookie);
        return ResponseEntity.ok("User registered successfully!");
    }

    @GetMapping("/account")
    public ResponseEntity<AccountDto> account(HttpServletRequest request) {
        System.out.println(request.getSession().getId());
        Optional<Account> account = authenticationService.account();
        return account.map(value -> ResponseEntity.ok(new AccountDto(value))).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping("/sign_in")
    public ResponseEntity<AccountDto> signIn(@RequestBody AccountSignInDto accountSignInDto,
                                             HttpServletRequest request,
                                             HttpServletResponse response) {
        System.out.println(request.getSession().getId());
        final Account account = authenticationService.signIn(new Account(accountSignInDto), request, response);
        return ResponseEntity.ok(new AccountDto(account));
    }
    @PostMapping("/sign_out")
    public ResponseEntity<?> signOut(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        System.out.println(request.getSession().getId());
        authenticationService.signOut(authentication, request, response);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/new_session")
    public ResponseEntity<AccountDto> newSession(HttpServletRequest request,
                                             HttpServletResponse response) throws UnexpectedRequestException, AppException, AlreadyExistsException {
        System.out.println(request.getSession().getId());
        final Account account = authenticationService.newSession(request, response);
        return ResponseEntity.ok(new AccountDto(account));
    }
}
