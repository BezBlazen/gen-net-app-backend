package bzblz.gen_net_app.model;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;
@Document(collection = "projects")
@Data
public class Project {
    @Id
    @GeneratedUUID
    private UUID id;

    @Version
    private Integer version;

    @CreatedDate
    private Date createdAt;

    @Indexed
    private UUID accountId;

    @NotEmpty(message = "Title should not be empty")
    private String title;

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
