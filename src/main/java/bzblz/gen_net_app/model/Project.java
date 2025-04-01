package bzblz.gen_net_app.model;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.UUID;
@Document(collection = "projects")
@Data
public class Project {
    @Id
    @GeneratedUUID
    private UUID id;

    @NotEmpty(message = "Title should not be empty")
//    @Size(min = 1, max = 20, message = "Name should be between 2 and 30 characters")
    private String title;


//    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private UUID accountId;

    public Project(String title, UUID accountId) {
        this.title = title;
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", accountId=" + accountId +
                '}';
    }
}
