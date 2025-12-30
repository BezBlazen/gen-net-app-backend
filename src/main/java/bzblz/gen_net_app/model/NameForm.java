package bzblz.gen_net_app.model;

import lombok.Data;

import java.util.List;

@Data
public class NameForm {
//    private Boolean preferred = false;
    private String fullText;
    private List<NamePart> parts;
}
