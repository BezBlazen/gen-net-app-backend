package bzblz.gen_net_app.services;

import bzblz.gen_net_app.model.User;
import bzblz.gen_net_app.repositories.URepository;
import bzblz.gen_net_app.security.UDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UDetailsService implements UserDetailsService {

    private final URepository uRepository;

    public UDetailsService(URepository uRepository) {
        this.uRepository = uRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = uRepository.findByUsername(username);
        if (user.isEmpty())
            throw new UsernameNotFoundException("User not found");
        return new UDetails(user.get());
    }
}
