package bzblz.gen_net_app.services;

import bzblz.gen_net_app.exceptions.AlreadyExistsException;
import bzblz.gen_net_app.model.Account;
import bzblz.gen_net_app.model.AccountRole;
import bzblz.gen_net_app.repositories.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountsService {
    private final AccountsRepository accountsRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountsService(AccountsRepository accountsRepository, PasswordEncoder passwordEncoder) {
        this.accountsRepository = accountsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Account add(Account account) throws AlreadyExistsException {
        if (account != null && !accountsRepository.findByUsername(account.getUsername()).isEmpty())
            throw new AlreadyExistsException();
        System.out.println(1);
        if (account != null) {
            account.setAccountRole(AccountRole.ROLE_USER);
            account.setPassword(passwordEncoder.encode(account.getPassword()));
        }
//        System.out.println(user);
        accountsRepository.save(account);
        return account;
    }
}
