package model;

/***
 * Case class modified for ElasticSearch indexing.
 * Every list is combined to String and mapped to coresponding property.
 * Extra properties (person, organization, location) extracted using NER (Named Entity Recognizer) and added so cases can be searched by these fields too
 */
public class IndexUnit {
    private String name;
    private String AustLII;
    private String catchphrases;
    private String sentences;
    private String person;
    private String organization;
    private String location;

    public IndexUnit(String name, String austLII, String catchphrases, String sentences, String person, String organization, String location) {
        this.name = name;
        AustLII = austLII;
        this.catchphrases = catchphrases;
        this.sentences = sentences;
        this.person = person;
        this.organization = organization;
        this.location = location;
    }

    public IndexUnit() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAustLII() {
        return AustLII;
    }

    public void setAustLII(String austLII) {
        AustLII = austLII;
    }

    public String getCatchphrases() {
        return catchphrases;
    }

    public void setCatchphrases(String catchphrases) {
        this.catchphrases = catchphrases;
    }

    public String getSentences() {
        return sentences;
    }

    public void setSentences(String sentences) {
        this.sentences = sentences;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return  "\"name\":\"" + name + "\"," +
                "\"AustLII\":\"" + AustLII + "\"," +
                "\"catchphrases\":\"" + catchphrases + "\"," +
                "\"sentences\":\"" + sentences + "\"," +
                "\"person\":\"" + person + "\"," +
                "\"organization\":\"" + organization + "\"," +
                "\"location\":\"" + location + "\"" ;
    }
}
