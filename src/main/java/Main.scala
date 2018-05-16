import com.liaozexiang.spark.util.JsonParser

import scala.io.Source

object Main {
  def main(args: Array[String]): Unit = {
    val contents = Source.fromFile("src/main/resources/json/users.json").mkString
    val objMap = new JsonParser(contents)
    val v = objMap.getJsonParser("property").getJsonParserList("c")
    v.foreach(j => println(j.getStringValue("x")))
    objMap.keySet.foreach(println)
  }
}
