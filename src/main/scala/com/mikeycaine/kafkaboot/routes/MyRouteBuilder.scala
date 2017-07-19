package com.mikeycaine.kafkaboot.routes

import org.apache.camel.CamelContext
import org.apache.camel.Exchange
import org.apache.camel.scala.dsl.builder.ScalaRouteBuilder
import org.apache.camel.support.TypeConverterSupport

import org.json4s.DefaultFormats
import org.json4s.Extraction
import org.json4s.TypeInfo
import org.json4s.native.JsonMethods.parse
import org.json4s.string2JsonInput

import org.springframework.stereotype.Component

import com.mikeycaine.kafkaboot.twitter.Tweet

class CaseClassTypeConverter extends TypeConverterSupport {
   implicit val formats = DefaultFormats
   def convertTo[T](clazz: Class[T], exchange: Exchange, value: Any) = {
      Extraction.extract(parse(value.toString), TypeInfo(clazz, None)).asInstanceOf[T]
   }
}

@Component
class MyRouteBuilder(override val context : CamelContext) extends ScalaRouteBuilder(context) {
  
  context.getTypeConverterRegistry.addTypeConverter(classOf[Tweet], classOf[String], new CaseClassTypeConverter)
  
  "kafka:MikesTweets?brokers=localhost:9092&groupId=myGroup&consumersCount=2&maxPollRecords=10000&seekTo=beginning" ==> {
     process {
       e: Exchange => {
         val tweet:Tweet = e.getIn.getBody(classOf[Tweet])
         println
         println("---------------------------------")
         println(s"${tweet.user.screen_name}: ${tweet.text}")
         println("---------------------------------")
       }
     }
  }
}




