package net.gemelen.dev.sgbplus.gate

import akka.actor.ActorSystem
import akka.event.slf4j.SLF4JLogging
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory

class Main {

}

object Main extends SLF4JLogging {

  val config = ConfigFactory.load()

  val serverAddress = config.getString("akka.http.address")
  val serverPort = config.getInt("akka.http.port")

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem(config.getString("akka.systemName"))
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    val route =
      path("hello") {
        get {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
        }
      }

    val server = Http().bindAndHandle(route, serverAddress, serverPort)

    server
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }
}