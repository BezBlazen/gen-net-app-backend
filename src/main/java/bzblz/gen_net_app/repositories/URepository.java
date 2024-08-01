package bzblz.gen_net_app.repositories;

import bzblz.gen_net_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface URepository extends JpaRepository<User, Integer> {
    public boolean existsUserByUsername(String username);
    public Optional<User> findByUsername(String username);

}
