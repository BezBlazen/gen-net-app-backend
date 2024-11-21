package bzblz.gen_net_app.dto;

import bzblz.gen_net_app.model.Account;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Setter
@Getter
public class AccountDto {
    private String username;
    private String role;

    public AccountDto(Account account) {
        this.username = account.getUsername();
        this.role = account.getRole().name();
    }
}
