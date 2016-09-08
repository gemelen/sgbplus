package net.gemelen.dev.sgbplus.gate

import akka.actor.ActorSystem
import akka.event.slf4j.SLF4JLogging
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory

import scala.concurrent.Await
import scala.concurrent.duration._

class Main {

}

object Main extends SLF4JLogging {

  val config = ConfigFactory.load()
  implicit val system: ActorSystem = ActorSystem(config.getString("akka.systemName"))
  implicit val executionContext = system.dispatcher
  implicit val materializer = ActorMaterializer()

  val serverAddress = config.getString("akka.http.address")
  val serverPort = config.getInt("akka.http.port")

  val routes = path("hello") {
    get {
      complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
    }
  }

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem(config.getString("akka.systemName"))

    scala.sys.addShutdownHook {
      system.terminate()
      Await.result(system.whenTerminated, 5 seconds)
    }

    val t = new Thread(new Runnable {
      override def run(): Unit = {
        Http().bindAndHandle(routes, serverAddress, serverPort)
      }
    })
  }
}