import java.io.{File, PrintWriter}
import java.util

import javax.xml.bind.{JAXBContext, JAXBException, Marshaller}
import model.{Case, Catchphrase, Sentence}

class XMLUtil {

  /***
    *
    * @param name represents a legal case name
    * @param url represents url where the case is originally found
    * @param catchphrases list of catchphrases containing some legal details
    * @param sentences list of sentences representing a script of the hearing on Court
    * @param filename name of the XML document which will be created
    * @throws exception if it's not possible to do the marshalling
    */
  @throws[JAXBException]
  def marshalingExample(name: String, url: String, catchphrases: util.ArrayList[Catchphrase], sentences: util.ArrayList[Sentence], filename: String): Unit = {
    val jaxbContext = JAXBContext.newInstance(classOf[Case])
    val jaxbMarshaller = jaxbContext.createMarshaller
    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)
    val c1 = new Case(name, url, catchphrases, sentences)
    jaxbMarshaller.marshal(c1 ,  new PrintWriter(filename + ".xml") )

  }

  /***
    *
    * @param filename path or just the file name of XML document that needs unmarshalling
    * @throws exception if XML document has bad formatting
    * @return statement
    */
  @throws[JAXBException]
  def unMarshalingExample(filename: String): Case = {

    val jaxbContext = JAXBContext.newInstance(classOf[Case])
    val jaxbUnmarshaller = jaxbContext.createUnmarshaller
    jaxbUnmarshaller.unmarshal(new File(filename)).asInstanceOf[Case]

  }


}
