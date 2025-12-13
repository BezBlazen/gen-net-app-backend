package bzblz.gen_net_app.model;

public enum NamePartType {
    Prefix("http://gedcomx.org/Prefix", ""),
    Suffix("http://gedcomx.org/Suffix", ""),
    Given("http://gedcomx.org/Given", ""),
    Surname("http://gedcomx.org/Surname", ""),
    Patronymic("http://gedcomx.org/Patronymic", ""),
    Matronymic("http://gedcomx.org/Matronymic", "")
    ;

    private final String uri;
    private final String description;

    NamePartType(String uri, String description) {
        this.uri = uri;
        this.description = description;
    }

    public String getUri() {
        return uri;
    }

    public String getDescription() {
        return description;
    }

    public static NamePartType fromUri(String uri) {
        for (NamePartType type : NamePartType.values()) {
            if (type.getUri().equals(uri)) {
                return type;
            }
        }
        return null;
    }
}
