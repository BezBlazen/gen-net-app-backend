package bzblz.gen_net_app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class AccountSignInDto {
    private String username;
    private String password;

    @Override
    public String toString() {
        return "AccountSignInDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
