package com.mikeycaine.kafkaboot.app

import org.springframework.context.annotation.Configuration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.boot.builder.SpringApplicationBuilder
import org.apache.camel.spring.boot.CamelSpringBootApplicationController

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = Array(
  "com.mikeycaine.kafkaboot.routes"
))
class MyConfig {
}

object Main {
  
  def main(args: Array[String]): Unit = {
    new SpringApplicationBuilder(classOf[MyConfig])
      .web(false)
      .run()
      .getBean(classOf[CamelSpringBootApplicationController])
      .blockMainThread
    }
}

