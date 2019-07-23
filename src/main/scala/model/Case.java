package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.stream.Collectors;

/***
 * Base class for unmarshalling XML document
 */
@XmlRootElement(name = "case")
@XmlAccessorType(XmlAccessType.FIELD)
public class Case {
    private String name;
    private String AustLII;
    @XmlElement(name = "catchphrases")
    private List<Catchphrase> catchphrases;
    @XmlElement(name = "sentences")
    private List<Sentence> sentences;

    public Case() {
    }

    public Case(String name, String austLII, List<Catchphrase> catchphrases, List<Sentence> sentences) {
        this.name = name;
        AustLII = austLII;
        this.catchphrases = catchphrases;
        this.sentences = sentences;
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

    public List<Catchphrase> getCatchphrases() {
        return catchphrases;
    }

    public void setCatchphrases(List<Catchphrase> catchphrases) {
        this.catchphrases = catchphrases;
    }

    public List<Sentence> getSentences() {
        return sentences;
    }

    public void setSentences(List<Sentence> sentences) {
        this.sentences = sentences;
    }

    public String catchphrasesToString(){
        return catchphrases.stream().map(Object::toString).collect(Collectors.joining(" "));
    }

    public String sentencesToString(){
        return sentences.stream().map(Object::toString).collect(Collectors.joining(" "));
    }

    @Override
    public String toString() {
        return name + " " + catchphrases.stream().map(Object::toString).collect(Collectors.joining(" ")) + " " + sentences.stream().map(Object::toString).collect(Collectors.joining(","));
    }
}
