package bzblz.gen_net_app.model;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Document(collection = "persons")
@Data
public class Person {
    @Id
    @GeneratedUUID
    private UUID id;

    @Indexed
    private UUID projectId;

    @Version
    private Integer version;

    @CreatedDate
    private Date createdAt;

    private Gender gender;
    private List<Name> names;
}
