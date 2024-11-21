package bzblz.gen_net_app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "project")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @NotEmpty(message = "Name should not be empty")
//    @Size(min = 2, max = 20, message = "Name should be between 2 and 30 characters")
    @Column(name = "title")
    private String title;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
//    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Account account;

    public Project(String title, Account account) {
        this.title = title;
        this.account = account;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", account=" + account +
                '}';
    }
}
