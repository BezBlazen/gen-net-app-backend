package bzblz.gen_net_app.model;

import bzblz.gen_net_app.dto.AccountSignInDto;
import bzblz.gen_net_app.dto.AccountSignUpDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "account")
@Getter
@Setter
public class Account implements Cloneable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 6, max = 64, message = "Length from 6 to 64")
    @NotBlank(message = "Username required")
    @Column(name = "username", nullable = false)
    private String username;

    @Size(min = 6, max = 64, message = "Length from 6 to 64")
    @NotBlank(message = "Password required")
    @Column(name = "password", nullable = false)
    private String password;

    @Email
    @Column(name = "email", unique = true)
    private String email;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private AccountRole role;

    public Account() {
    }
    public Account(@NonNull AccountSignUpDto accountSignUpDto) {
        this.username = accountSignUpDto.getUsername();
        this.password = accountSignUpDto.getPassword();
    }
    public Account(@NonNull AccountSignInDto accountSignInDto) {
        this.username = accountSignInDto.getUsername();
        this.password = accountSignInDto.getPassword();
    }
    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public Account(String username, String password, AccountRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Override
    public String toString() {
        return "Account {" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", accountRole='" + (role != null ? role.name() : null) + '\'' +
                '}';
    }

    @Override
    public Account clone() {
        try {
            return (Account) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
