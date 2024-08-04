package bzblz.gen_net_app.services;

import bzblz.gen_net_app.exceptions.AlreadyExistsException;
import bzblz.gen_net_app.model.User;
import bzblz.gen_net_app.model.UserRole;
import bzblz.gen_net_app.repositories.URepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UService {
    private final URepository uRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UService(URepository uRepository, PasswordEncoder passwordEncoder) {
        this.uRepository = uRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User add(User user) throws AlreadyExistsException {
        if (user != null && !uRepository.findByUsername(user.getUsername()).isEmpty())
            throw new AlreadyExistsException();
        System.out.println(1);
        if (user != null) {
            user.setUserRole(UserRole.ROLE_USER);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
//        System.out.println(user);
        uRepository.save(user);
        return user;
    }
}
