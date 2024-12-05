package bzblz.gen_net_app.services;

import bzblz.gen_net_app.controllers.ApiController;
import bzblz.gen_net_app.dto.AccountSignInDto;
import bzblz.gen_net_app.exceptions.AlreadyExistsException;
import bzblz.gen_net_app.exceptions.AppException;
import bzblz.gen_net_app.exceptions.UnexpectedRequestException;
import bzblz.gen_net_app.model.Account;
import bzblz.gen_net_app.model.AccountRole;
import bzblz.gen_net_app.security.AccountDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AccountService accountService;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private final SecurityContextRepository securityContextRepository;
    private final SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    public Account authenticate(AccountSignInDto account) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        account.getUsername(),
                        account.getPassword()
                )
        );
        return accountService.findByUsername(account.getUsername()).orElseThrow();
    }

    public Account signUp(@NonNull Account account) throws AlreadyExistsException, AppException {
        return accountService.addAccount(account);
    }

    public void signOut(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        this.logoutHandler.logout(request, response, authentication);
    }

    public Account signIn(@NonNull Account account,
                          HttpServletRequest request,
                          HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        account.getUsername(),
                        account.getPassword()
                )
        );
        SecurityContext context = securityContextHolderStrategy.createEmptyContext();
        context.setAuthentication(authentication);
        securityContextHolderStrategy.setContext(context);
        securityContextRepository.saveContext(context, request, response);

        return accountService.findByUsername(account.getUsername()).orElse(null);
    }

    public Account newSession(HttpServletRequest request,
                              HttpServletResponse response) throws UnexpectedRequestException, AlreadyExistsException, AppException {
        SecurityContext context = SecurityContextHolder.getContext() ;
        if (!(context.getAuthentication() instanceof AnonymousAuthenticationToken))
            throw new UnexpectedRequestException("Unexpected request");

        if (StringUtils.isBlank(request.getSession().getId()))
            throw new UnexpectedRequestException("Unexpected request");

        Account account = new Account("~" + request.getSession().getId(), UUID.randomUUID().toString(), AccountRole.ROLE_SESSION);
        signUp(account.clone());
        return signIn(account, request, response);
    }
    public Optional<Account> account() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context.getAuthentication() instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        } else {
            return Optional.of(((AccountDetails)context.getAuthentication().getPrincipal()).getAccount());
        }
    }
}
