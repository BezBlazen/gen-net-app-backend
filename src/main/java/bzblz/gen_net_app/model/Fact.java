package bzblz.gen_net_app.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "facts")
@Data
public class Fact {
    private FactType type;
    private String date;
    private String place;
}
