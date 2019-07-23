import java.io.{File, PrintWriter}
import java.util

import javax.xml.bind.{JAXBContext, JAXBException, Marshaller}
import model.{Case, Catchphrase, Sentence}
import org.springframework.http.{HttpEntity, HttpHeaders, HttpMethod, MediaType}
import org.springframework.web.client.{HttpClientErrorException, RestTemplate}

object CaseIndex extends App {

  override def main(args: Array[String]): Unit={

    val folder = new File(args(0)) //first argument is path to a directory containing xml case files
    val listOfFiles = folder.listFiles()
    val i = 0
    var bulk = ""
    val fileUtils = new FileIndexUtil
    val xmlUtils = new XMLUtil

    //creating a statement for bulk indexing
    for(i<-0 to listOfFiles.length-1){
      val filename = listOfFiles(i).getName()
      if(filename.endsWith(".xml")||filename.endsWith(".XML"))
      {
        System.out.println("Found: " + filename)
        val legalCase = xmlUtils.unMarshalingExample(filename)
        val response = fileUtils.sendToNLPServer(legalCase)
        val statement = fileUtils.caseToIndexingStatement(response, legalCase)
        bulk += statement + java.lang.System.getProperty("line.separator")

      }
    }
    //after creating a string for bulk request body, we index it all on elasticsearch
   fileUtils.sendToElasticSearch(bulk)

  }


}
