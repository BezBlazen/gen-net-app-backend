package bzblz.gen_net_app.model;

public enum NameType {
    BirthName("http://gedcomx.org/BirthName", "Name given at birth"),
    DeathName("http://gedcomx.org/DeathName", "Name used at the time of death"),
    MarriedName("http://gedcomx.org/MarriedName", "Name accepted at marriage"),
    AlsoKnownAs("http://gedcomx.org/AlsoKnownAs", "\"Also known as\" name"),
    Nickname("http://gedcomx.org/Nickname", "Nickname"),
    AdoptiveName("http://gedcomx.org/AdoptiveName", "Name given at adoption"),
    FormalName("http://gedcomx.org/FormalName", "A formal name, usually given to distinguish it from a name more commonly used"),
    ReligiousName("http://gedcomx.org/ReligiousName", "A name given at a religious rite or ceremony");

    private final String uri;
    private final String description;

    NameType(String uri, String description) {
        this.uri = uri;
        this.description = description;
    }

    public String getUri() {
        return uri;
    }

    public String getDescription() {
        return description;
    }

    public static NameType fromUri(String uri) {
        for (NameType type : NameType.values()) {
            if (type.getUri().equals(uri)) {
                return type;
            }
        }
        return null;
    }
}
