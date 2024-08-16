package bzblz.gen_net_app.services;

import bzblz.gen_net_app.exceptions.AlreadyExistsException;
import bzblz.gen_net_app.model.Account;
import bzblz.gen_net_app.model.AccountRole;
import bzblz.gen_net_app.model.Project;
import bzblz.gen_net_app.repositories.AccountsRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class AccountsService {
    private final ProjectsService projectsService;
    private final AccountsRepository accountsRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountsService(ProjectsService projectsService, AccountsRepository accountsRepository, PasswordEncoder passwordEncoder) {
        this.projectsService = projectsService;
        this.accountsRepository = accountsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Account addUserAccount(Account account) throws AlreadyExistsException {
        if (account != null && !accountsRepository.findByUsername(account.getUsername()).isEmpty())
            throw new AlreadyExistsException(String.format("Account '%s' already exist", account));

        if (account != null) {
            account.setAccountRole(AccountRole.ROLE_USER);
            account.setPassword(passwordEncoder.encode(account.getPassword()));
        }

        accountsRepository.save(account);
        projectsService.add(account, "Default");
        return account;
    }
    @Transactional
    public Account ensureSessionAccount(String sessionId) {
        final String sessionAccountName = StringUtils.isNotBlank(sessionId) ? "_" + sessionId : null;

        if (sessionAccountName == null || !accountsRepository.findByUsername(sessionAccountName).isEmpty())
            return null;

        final Account sessionAccount = new Account(sessionAccountName, UUID.randomUUID().toString());
        sessionAccount.setAccountRole(AccountRole.ROLE_SESSION);
        accountsRepository.save(sessionAccount);
        projectsService.add(sessionAccount, "Default");

        return sessionAccount;
    }
    public Optional<Account> findByUsername(String username) {
        System.out.println("findByUsername: " + username);
        return accountsRepository.findByUsername(username);
    }
}
