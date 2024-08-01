package bzblz.gen_net_app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Can't be empty")
    @Size(min = 6, max = 20, message = "Allowed length from 6 to 20")
    @Column(name = "username")
    private String username;

    @NotEmpty(message = "Can't be empty")
    @Size(min = 6, max = 64, message = "Allowed length from 6 to 64")
    @Column(name = "password")
    private String password;

    @NotEmpty
    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    public User() {
    }

    public User(String username, String password) {
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

    public UserRole getUserRole() {
        return userRole;
    }
    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userRole='" + (userRole != null ? userRole.name() : null) + '\'' +
                '}';
    }
}
