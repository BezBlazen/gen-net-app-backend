package bzblz.gen_net_app.dto;

import bzblz.gen_net_app.model.User;
import bzblz.gen_net_app.model.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    @NotEmpty(message = "Can't be empty")
    private String username;

    public UserDto(User user) {
        this.username = user.getUsername();
    }
}
