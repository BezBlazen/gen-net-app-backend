package bzblz.gen_net_app.repositories;

import bzblz.gen_net_app.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends MongoRepository<Account, UUID> {
    public Optional<Account> findByUsername(String username);
    public Optional<Account> findByEmail(String email);
    public boolean existsByUsername(String username);
}
