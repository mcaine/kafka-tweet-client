package com.mikeycaine.kafkaboot.routes

import org.apache.camel.CamelContext
import org.apache.camel.scala.dsl.builder.ScalaRouteBuilder
import org.springframework.stereotype.Component
import com.mikeycaine.kafkaboot.twitter.Tweet
import org.apache.camel.component.jackson.JacksonDataFormat
import com.fasterxml.jackson.databind.DeserializationFeature

@Component
class MyRouteBuilder(override val context : CamelContext) extends ScalaRouteBuilder(context) {
  
  val df = new JacksonDataFormat(classOf[Tweet])
  df.disableFeature(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
  df.disableFeature(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
  
   from("direct:kafkaJson")
    //.unmarshal(df)
    .log("${body}")
  
  from("kafka:replicated-kafkatopic?brokers=localhost:9093&groupId=myGroup&consumersCount=2&maxPollRecords=10000&seekTo=beginning")
    .to("direct:kafkaJson")
    

    
}
