package model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "catchphrase")
public class Catchphrase {

    String id;

    String catchphrase;

    public Catchphrase() {
    }
    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Catchphrase(String catchphrase) {
        this.catchphrase = catchphrase;
    }

    public String getCatchphrase() {
        return catchphrase;
    }

    public void setCatchphrase(String catchphrase) {
        this.catchphrase = catchphrase;
    }

    @Override
    public String toString() {
        return catchphrase;
    }
}
