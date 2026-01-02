package bzblz.gen_net_app.model;

import lombok.Data;

import java.util.List;

@Data
public class NameForm {
    private String fullText;
    private List<NamePart> parts;
}
