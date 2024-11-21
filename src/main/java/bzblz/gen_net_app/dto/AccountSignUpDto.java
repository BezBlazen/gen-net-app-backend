package bzblz.gen_net_app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AccountSignUpDto {
    private String username;
    private String password;
}