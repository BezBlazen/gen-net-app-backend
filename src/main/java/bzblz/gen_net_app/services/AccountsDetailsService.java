package bzblz.gen_net_app.services;

import bzblz.gen_net_app.model.Account;
import bzblz.gen_net_app.repositories.AccountsRepository;
import bzblz.gen_net_app.security.AccountsDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountsDetailsService implements UserDetailsService {

    private final AccountsRepository accountsRepository;

    public AccountsDetailsService(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> user = accountsRepository.findByUsername(username);
        if (user.isEmpty())
            throw new UsernameNotFoundException("User not found");
        return new AccountsDetails(user.get());
    }
}
