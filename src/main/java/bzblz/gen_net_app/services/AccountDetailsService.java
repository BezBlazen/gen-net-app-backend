package bzblz.gen_net_app.services;

import bzblz.gen_net_app.model.Account;
import bzblz.gen_net_app.repositories.AccountRepository;
import bzblz.gen_net_app.security.AccountDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;

    public AccountDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> user = accountRepository.findByUsername(username);
        if (user.isEmpty())
            throw new UsernameNotFoundException("User not found");
        return new AccountDetails(user.get());
    }
}
