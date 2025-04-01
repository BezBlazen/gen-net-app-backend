package bzblz.gen_net_app.model;

import bzblz.gen_net_app.dto.AccountSignInDto;
import bzblz.gen_net_app.dto.AccountSignUpDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.UUID;

@Document(collection = "accounts")
@Data
public class Account implements Cloneable {
    @Id
    @GeneratedUUID
    private UUID id;

    @Size(min = 6, max = 64, message = "Username - Length from 6 to 64")
    @NotBlank(message = "Username required")
    private String username;

    @Size(min = 6, max = 64, message = "Password - Length from 6 to 64")
    @NotBlank(message = "Password required")
    private String password;

    @Email
    private String email;

    private Date createdAt;
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

