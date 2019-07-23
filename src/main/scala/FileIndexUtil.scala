import com.google.gson.{Gson, JsonObject}
import model.{Case, IndexUnit}
import org.codehaus.jettison.json.{JSONArray, JSONObject}
import org.springframework.http.{HttpEntity, HttpHeaders, HttpMethod, MediaType}
import org.springframework.web.client.{HttpClientErrorException, RestTemplate}
import java.util.UUID.randomUUID
/***
  * Utilities for communication with NLP server and ElasticSearch
  */
class FileIndexUtil() {

  /***
    * sends legal case object's string representation to NLP server to go trough NER so we can extract persons, organisations and locations from XML document
    *
    * @param legalCase is the object created after unmarshalling given XMl doucment
    * @return a response with tokens created from the XML document contents which tell us are there any persons, organisations or locations mentioned
    */
  def sendToNLPServer(legalCase: Case): String ={
    val restTemplate = new RestTemplate()
    val url = "http://localhost:9000/?properties=%7B%22annotators%22%3A%20%22tokenize%2Cssplit%2Cpos%2Clemma%2Cner%22%2C%20%22date%22%3A%20%222019-07-22T19%3A58%3A15%22%2C%22timeout%22%3A50000%7D&pipelineLanguage=en"
    restTemplate.postForObject(url, legalCase.toString, classOf[String])
  }

  /***
    *
    * creates a statement that will later go to bulk indexing request on ElasticSearch
    * @param response is a response from NLP server, XML content split into tokens classified as a person, organisation, location or a regular word
    * @param legalCase is the orginal object created after unmarshalling given XML document
    * @return a statement to index given XML document
    */
  def caseToIndexingStatement(response: String, legalCase: Case): String ={

    val gson = new Gson()
    val jsonObj = gson.fromJson(response, classOf[JsonObject]);
    val sentences = jsonObj.get("sentences");
    val elements = sentences.getAsJsonArray.get(0)
    val tokenHolder = elements.getAsJsonObject().entrySet().toArray()(2)
    val stringNode = tokenHolder.toString.substring(7)
    val tokens = new JSONArray(stringNode)

    var persons, organisations, locations = ""
    for (i <- 0 to tokens.length() - 1) {
    val dict = tokens.get(i).asInstanceOf[JSONObject]
    if(dict.get("ner").equals("ORGANIZATION"))
    organisations += " " +  dict.get("originalText")
    else if(dict.get("ner").equals("PERSON"))
    persons += " " +  dict.get("originalText")

    else if(dict.get("ner").equals("LOCATION"))
    locations += " " +  dict.get("originalText")

  }
    val indexUnit = new IndexUnit(legalCase.getName, legalCase.getAustLII, legalCase.sentencesToString, legalCase.catchphrasesToString, persons, organisations, locations)
   "{\"index\":{\"_index\":\"legal_idx\", \"_type\": \"cases\", \"_id\":\"" + randomUUID().toString + "\"}} "  + java.lang.System.getProperty("line.separator") +  "{" + indexUnit.toString + "}"

  }

  /***
    * Sends final bulk statement to ElasticSearch
    * @param bulk all XML documents as CaseIndex string representatioins in one statement for bulk indexing request
    */
  def sendToElasticSearch(bulk: String): Unit = {
    val restTemplate = new RestTemplate()
    var headers = new HttpHeaders()
    headers.setContentType(MediaType.APPLICATION_JSON_UTF8)
    val entity = new HttpEntity[String](bulk, headers)
    println(bulk)
    try {
      println("Indexing data...")
      val response = restTemplate.exchange("http://localhost:9200/_bulk", HttpMethod.POST, entity, classOf[String])
      println("Data successfuly indexed.")
    }catch{
      case e: HttpClientErrorException =>{
        println(e.getResponseBodyAsString)
      }
    }
  }


}