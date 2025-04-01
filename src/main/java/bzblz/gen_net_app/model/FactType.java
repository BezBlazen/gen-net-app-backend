package bzblz.gen_net_app.model;

public enum FactType {
    Adoption("http://gedcomx.org/Adoption", "A fact of a person's adoption."),
    AdultChristening("http://gedcomx.org/AdultChristening", "A fact of a person's christening or baptism as an adult."),
    Amnesty("http://gedcomx.org/Amnesty", "A fact of a person's amnesty."),
    AncestralHall("http://gedcomx.org/AncestralHall", "A fact of a person's ancestral hall. An ancestral hall refers to a location where the early ancestors of the person originated. It may also refer to the name of an early ancestor. Family clans are often distinguished one from another by the ancestral hall. Clans that cannot prove direct relationships to other clans with the same surname can assume a direct relationship if they share the same ancestral hall."),
    AncestralPoem("http://gedcomx.org/AncestralPoem", "A fact of a person's ancestral poem. An ancestral poem (or generation poem) is composed of the \"generation characters\" that are to be used when choosing names for the members of different generations of an extended family. Ancestral poems are prominent in Asian countries, particularly China."),
    Apprenticeship("http://gedcomx.org/Apprenticeship", "A fact of a person's apprenticeship."),
    Arrest("http://gedcomx.org/Arrest", "A fact of a person's arrest."),
    Award("http://gedcomx.org/Award", "A fact of a person's award (medal, honor)."),
    Baptism("http://gedcomx.org/Baptism", "A fact of a person's baptism."),
    BarMitzvah("http://gedcomx.org/BarMitzvah", "A fact of a person's bar mitzvah."),
    BatMitzvah("http://gedcomx.org/BatMitzvah", "A fact of a person's bat mitzvah."),
    Birth("http://gedcomx.org/Birth", "A fact of a person's birth."),
    BirthNotice("http://gedcomx.org/BirthNotice", "A fact of a person's birth notice, such as posted in a newspaper or other publishing medium."),
    Blessing("http://gedcomx.org/Blessing", "A fact of an official blessing received by a person, such as at the hands of a clergy member or at another religious rite."),
    Branch("http://gedcomx.org/Branch", "A fact of a person's branch within an extended clan."),
    Burial("http://gedcomx.org/Burial", "A fact of the burial of a person's body after death."),
    Caste("http://gedcomx.org/Caste", "A fact of a person's caste."),
    Census("http://gedcomx.org/Census", "A fact of a person's participation in a census."),
    Christening("http://gedcomx.org/Christening", "A fact of a person's christening at birth. Note: Use AdultChristening for the christening as an adult."),
    Circumcision("http://gedcomx.org/Circumcision", "A fact of a person's circumcision."),
    Clan("http://gedcomx.org/Clan", "A fact of a person's clan."),
    Confirmation("http://gedcomx.org/Confirmation", "A fact of a person's confirmation (or other rite of initiation) in a church or religion."),
    Court("http://gedcomx.org/Court", "A fact of the appearance of a person in a court proceeding."),
    Cremation("http://gedcomx.org/Cremation", "A fact of the cremation of person's body after death."),
    Death("http://gedcomx.org/Death", "A fact of the death of a person."),
    Education("http://gedcomx.org/Education", "A fact of an education or an educational achievement (e.g., diploma, graduation, scholarship, etc.) of a person."),
    EducationEnrollment("http://gedcomx.org/EducationEnrollment", "A fact of a person's enrollment in an educational program or institution."),
    Emigration("http://gedcomx.org/Emigration", "A fact of the emigration of a person."),
    Enslavement("http://gedcomx.org/Enslavement", "A fact of the enslavement of a person."),
    Ethnicity("http://gedcomx.org/Ethnicity", "A fact of a person's ethnicity."),
    Excommunication("http://gedcomx.org/Excommunication", "A fact of a person's excommunication from a church."),
    FirstCommunion("http://gedcomx.org/FirstCommunion", "A fact of a person's first communion in a church."),
    Funeral("http://gedcomx.org/Funeral", "A fact of a person's funeral."),
    GenderChange("http://gedcomx.org/GenderChange", "A fact of a person's gender change."),
    GenerationNumber("http://gedcomx.org/GenerationNumber", "A fact of a person's generation number, indicating the number of generations the person is removed from a known \"first\" ancestor."),
    Graduation("http://gedcomx.org/Graduation", "A fact of a person's graduation from a scholastic institution."),
    Heimat("http://gedcomx.org/Heimat", "A fact of a person's heimat. \"Heimat\" refers to a person's affiliation by birth to a specific geographic place. Distinct heimaten are often useful as indicators that two persons of the same name are not likely to be closely related genealogically. In English, \"heimat\" may be described using terms like \"ancestral home\", \"homeland\", or \"place of origin\"."),
    Immigration("http://gedcomx.org/Immigration", "A fact of a person's immigration."),
    Imprisonment("http://gedcomx.org/Imprisonment", "A fact of a person's imprisonment."),
    Inquest("http://gedcomx.org/Inquest", "A legal inquest. Inquests usually only occur when there’s something suspicious about the death. Inquests might in some instances lead to a murder investigation. Most people that die have a death certificate wherein a doctor indicates the cause of death and often indicates when the decedent was last seen by that physician; these require no inquest."),
    LandTransaction("http://gedcomx.org/LandTransaction", "A fact of a land transaction enacted by a person."),
    Language("http://gedcomx.org/Language", "A fact of a language spoken by a person."),
    Living("http://gedcomx.org/Living", "A fact of a record of a person's living for a specific period. This is designed to include \"flourish\", defined to mean the time period in an adult's life where he was most productive, perhaps as a writer or member of the state assembly. It does not reflect the person's birth and death dates."),
    MaritalStatus("http://gedcomx.org/MaritalStatus", "A fact of a person's marital status."),
    Medical("http://gedcomx.org/Medical", "A fact of a person's medical record, such as for an illness or hospital stay."),
    MilitaryAward("http://gedcomx.org/MilitaryAward", "A fact of a person's military award."),
    MilitaryDischarge("http://gedcomx.org/MilitaryDischarge", "A fact of a person's military discharge."),
    MilitaryDraftRegistration("http://gedcomx.org/MilitaryDraftRegistration", "A fact of a person's registration for a military draft."),
    MilitaryInduction("http://gedcomx.org/MilitaryInduction", "A fact of a person's military induction."),
    MilitaryService("http://gedcomx.org/MilitaryService", "A fact of a person's military service."),
    Mission("http://gedcomx.org/Mission", "A fact of a person's church mission."),
    MoveFrom("http://gedcomx.org/MoveFrom", "A fact of a person's move (i.e., change of residence) from a location."),
    MoveTo("http://gedcomx.org/MoveTo", "A fact of a person's move (i.e., change of residence) to a new location."),
    MultipleBirth("http://gedcomx.org/MultipleBirth", "A fact that a person was born as part of a multiple birth (e.g., twin, triplet, etc.)."),
    NationalId("http://gedcomx.org/NationalId", "A fact of a person's national id (e.g., social security number)."),
    Nationality("http://gedcomx.org/Nationality", "A fact of a person's nationality."),
    Naturalization("http://gedcomx.org/Naturalization", "A fact of a person's naturalization (i.e., acquisition of citizenship and nationality)."),
    NumberOfChildren("http://gedcomx.org/NumberOfChildren", "A fact of the number of children of a person or relationship."),
    NumberOfMarriages("http://gedcomx.org/NumberOfMarriages", "A fact of a person's number of marriages."),
    Obituary("http://gedcomx.org/Obituary", "A fact of a person's obituary."),
    OfficialPosition("http://gedcomx.org/OfficialPosition", "A fact of a person's official (government) position."),
    Occupation("http://gedcomx.org/Occupation", "A fact of a person's occupation or employment."),
    Ordination("http://gedcomx.org/Ordination", "A fact of a person's ordination to a stewardship in a church."),
    Pardon("http://gedcomx.org/Pardon", "A fact of a person's legal pardon."),
    PhysicalDescription("http://gedcomx.org/PhysicalDescription", "A fact of a person's physical description."),
    Probate("http://gedcomx.org/Probate", "A fact of a receipt of probate of a person's property."),
    Property("http://gedcomx.org/Property", "A fact of a person's property or possessions."),
    Race("http://gedcomx.org/Race", "A fact of the declaration of a person's race, presumably in a historical document."),
    Religion("http://gedcomx.org/Religion", "A fact of a person's religion."),
    Residence("http://gedcomx.org/Residence", "A fact of a person's residence."),
    Retirement("http://gedcomx.org/Retirement", "A fact of a person's retirement."),
    Stillbirth("http://gedcomx.org/Stillbirth", "A fact of a person's stillbirth."),
    TaxAssessment("http://gedcomx.org/TaxAssessment", "A fact of a person's tax assessment."),
    Tribe("http://gedcomx.org/Tribe", "A fact of a person's tribe."),
    Will("http://gedcomx.org/Will", "A fact of a person's will."),
    Visit("http://gedcomx.org/Visit", "A fact of a person's visit to a place different from the person's residence."),
    Yahrzeit("http://gedcomx.org/Yahrzeit", "A fact of a person's yahrzeit date. A person's yahrzeit is the anniversary of their death as measured by the Hebrew calendar."),
    Annulment("http://gedcomx.org/Annulment", "The fact of an annulment of a marriage."),
    CommonLawMarriage("http://gedcomx.org/CommonLawMarriage", "The fact of a marriage by common law."),
    CivilUnion("http://gedcomx.org/CivilUnion", "The fact of a civil union of a couple."),
    Divorce("http://gedcomx.org/Divorce", "The fact of a divorce of a couple."),
    DivorceFiling("http://gedcomx.org/DivorceFiling", "The fact of a filing for divorce."),
    DomesticPartnership("http://gedcomx.org/DomesticPartnership", "The fact of a domestic partnership of a couple."),
    Engagement("http://gedcomx.org/Engagement", "The fact of an engagement to be married."),
    Marriage("http://gedcomx.org/Marriage", "The fact of a marriage."),
    MarriageBanns("http://gedcomx.org/MarriageBanns", "The fact of a marriage banns."),
    MarriageContract("http://gedcomx.org/MarriageContract", "The fact of a marriage contract."),
    MarriageLicense("http://gedcomx.org/MarriageLicense", "The fact of a marriage license."),
    MarriageNotice("http://gedcomx.org/MarriageNotice", "The fact of a marriage notice."),
    Separation("http://gedcomx.org/Separation", "A fact of a couple's separation."),
    AdoptiveParent("http://gedcomx.org/AdoptiveParent", "A fact about an adoptive relationship between a parent and a child."),
    BiologicalParent("http://gedcomx.org/BiologicalParent", "A fact about the biological relationship between a parent and a child."),
    ChildOrder("http://gedcomx.org/ChildOrder", "A fact about the child order between a parent and a child."),
    EnteringHeir("http://gedcomx.org/EnteringHeir", "A fact about an entering heir relationship between a parent and a child. An entering heir is received from another parent as an \"exiting heir\" for designation of inheritance."),
    ExitingHeir("http://gedcomx.org/ExitingHeir", "A fact about an exiting heir relationship between a parent and a child. An exiting heir is given as an \"entering heir\" to another parent for designation of inheritance."),
    FosterParent("http://gedcomx.org/FosterParent", "A fact about a foster relationship between a foster parent and a child."),
    GuardianParent("http://gedcomx.org/GuardianParent", "A fact about a legal guardianship between a parent and a child."),
    StepParent("http://gedcomx.org/StepParent", "A fact about the step relationship between a parent and a child."),
    SociologicalParent("http://gedcomx.org/SociologicalParent", "A fact about a sociological relationship between a parent and a child, but not definable in typical legal or biological terms."),
    SurrogateParent("http://gedcomx.org/SurrogateParent", "A fact about a pregnancy surrogate relationship between a parent and a child.");

    private final String uri;
    private final String description;

    FactType(String uri, String description) {
        this.uri = uri;
        this.description = description;
    }

    public String getUri() {
        return uri;
    }

    public String getDescription() {
        return description;
    }

    public static FactType fromUri(String uri) {
        for (FactType type : FactType.values()) {
            if (type.getUri().equals(uri)) {
                return type;
            }
        }
        return null;
    }
}
