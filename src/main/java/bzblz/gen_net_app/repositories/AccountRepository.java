package bzblz.gen_net_app.repositories;

import bzblz.gen_net_app.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    public Optional<Account> findByUsername(String username);
    public Optional<Account> findByEmail(String email);
    public boolean existsByUsername(String username);
}
