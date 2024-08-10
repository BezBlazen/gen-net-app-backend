package bzblz.gen_net_app.dto;

import bzblz.gen_net_app.model.Account;
import bzblz.gen_net_app.model.Project;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AccountDto {
    @NotEmpty(message = "Can't be empty")
    private String username;

    private List<Project> projects;

    public AccountDto(Account account) {
        this.username = account.getUsername();
        this.projects = account.getProjects();
    }
}
