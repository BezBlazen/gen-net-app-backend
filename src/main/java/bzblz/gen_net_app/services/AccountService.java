package bzblz.gen_net_app.services;

import bzblz.gen_net_app.exceptions.AlreadyExistsException;
import bzblz.gen_net_app.exceptions.AppException;
import bzblz.gen_net_app.model.Account;
import bzblz.gen_net_app.model.AccountRole;
import bzblz.gen_net_app.repositories.AccountRepository;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProjectService projectService;

    @Autowired
    public AccountService(ProjectService projectService, AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.projectService = projectService;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Account addAccountWithRoleUser(@NonNull Account account) throws AlreadyExistsException {
        if (accountRepository.findByUsername(account.getUsername()).isPresent())
            throw new AlreadyExistsException(String.format("Account '%s' already exist", account.getUsername()));

        account.setRole(AccountRole.ROLE_USER);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountRepository.save(account);
        projectService.add(account, "Default");

        return account;
    }
    @Transactional
    public Account addAccount(@NonNull Account account) throws AlreadyExistsException, AppException {
        if (account.getRole() == null)
            throw new AppException("Account role undefined");
        if (accountRepository.findByUsername(account.getUsername()).isPresent())
            throw new AlreadyExistsException(String.format("Account '%s' already exist", account.getUsername()));
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountRepository.save(account);
        return account;
    }
    @Transactional
    public Account ensureSessionAccount(String sessionId) {
        final String sessionAccountName = StringUtils.isNotBlank(sessionId) ? "_" + sessionId : null;

        if (sessionAccountName == null || !accountRepository.findByUsername(sessionAccountName).isEmpty())
            return null;

        final Account sessionAccount = new Account(sessionAccountName, UUID.randomUUID().toString());
        sessionAccount.setRole(AccountRole.ROLE_SESSION);
        accountRepository.save(sessionAccount);
        projectService.add(sessionAccount, "Default");

        return sessionAccount;
    }
    public Optional<Account> findByUsername(String username) {
        System.out.println("findByUsername: " + username);
        return accountRepository.findByUsername(username);
    }
}
