package bzblz.gen_net_app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "account")
@Getter
@Setter
public class Account {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//    @NotEmpty(message = "Can't be empty")
//    @Size(min = 6, max = 20, message = "Allowed length from 6 to 20")
    @Column(name = "username")
    private String username;

//    @NotEmpty(message = "Can't be empty")
//    @Size(min = 6, max = 64, message = "Allowed length from 6 to 64")
    @Column(name = "password")
    private String password;

//    @NotNull
//    @UserRolePattern(regexp = "ROLE_ADMIN|ROLE_USER")
    @Column(name = "account_role")
    @Enumerated(EnumType.STRING)
    private AccountRole accountRole;

    @OneToMany(mappedBy="account")
    private List<Project> projects;

    public Account() {
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountRole getAccountRole() {
        return accountRole;
    }
    public void setAccountRole(AccountRole accountRole) {
        this.accountRole = accountRole;
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", accountRole='" + (accountRole != null ? accountRole.name() : null) + '\'' +
                '}';
    }
}
