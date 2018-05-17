package com.liaozexiang.spark.util

import scala.util.parsing.json.JSON

class JsonParser private() {

  private var jsonMap: Map[String, Any] = Map.empty[String, Any]

  def this(str: String) {
    this()
    val obj = JSON.parseFull(str)
    obj match {
      case Some(map: Map[String, Any]) => jsonMap = map
      case None => {
        throw new ClassCastException("parse json failed!")
      }
    }
  }

  def this(jMap: Map[String, Any]) {
    this()
    jsonMap = jMap
  }


  def getStringValue(key: String) = {
    val v = jsonMap.get(key)
    if (v.isDefined) v.get.toString
    else throw new Exception(s"no key named $key")
  }

  def getIntValue(key: String) = {
    val v = jsonMap.get(key)
    if (v.isDefined) {
      val d = v.get.toString.toDouble
      if (d - d.toInt == 0) d.toInt
      else {
        throw new ClassCastException("not a double value")
      }
    }
    else throw new Exception(s"no key named $key")
  }

  def getDoubleValue(key: String) = {
    val v = jsonMap.get(key)
    if (v.isDefined) v.get.toString.toDouble
    else throw new Exception(s"no key named $key")
  }

  def getList[T](key: String) = {
    val v = jsonMap.get(key)
    if (v.isDefined) v.get.asInstanceOf[List[T]]
    else throw new Exception(s"no key named $key")
  }

  def getJsonParser(key: String) = {
    val v = jsonMap.get(key)
    if (v.isDefined) new JsonParser(v.get.asInstanceOf[Map[String, Any]])
    else throw new Exception(s"no key named $key")
  }

  def getJsonParserList(key: String) = {
    val v = jsonMap.get(key)
    if (v.isDefined)
      v.get.asInstanceOf[List[Map[String, Any]]]
        .map(jMap => new JsonParser(jMap))
    else throw new Exception(s"no key named $key")
  }

  def keySet = jsonMap.keySet

  override def toString: String = {
    jsonMap.toString()
  }
}
