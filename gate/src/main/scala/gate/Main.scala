package net.gemelen.dev.sgbplus.gate

import akka.actor.ActorSystem
import akka.event.slf4j.SLF4JLogging
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory

import scala.concurrent.Await
import scala.concurrent.duration._

class Main {

}

object Main extends SLF4JLogging {

  val config = ConfigFactory.load()
  implicit val system: ActorSystem = ActorSystem(config.getString("akka.systemName"), config.getConfig("akka"))
  implicit val executionContext = system.dispatcher
  implicit val materializer = ActorMaterializer()

  val serverAddress = config.getString("akka.http.address")
  val serverPort = config.getInt("akka.http.port")

  val routes =
    path("login") {
      post {
        entity(as[String]) { body =>
          complete(handleLoginRequest(body))
        }
      }
    } ~
      path("token") {
        get {
          complete(handleTokenRequest())
        }
      } ~
      path("logout") {
        post {
          entity(as[String]) { body =>
            complete(handleLogoutRequest(body))
          }
        }
      }

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem(config.getString("akka.systemName"), config.getConfig("akka"))

    scala.sys.addShutdownHook {
      log.info("shutting down")
      system.terminate()
      Await.result(system.whenTerminated, 5 seconds)
    }

    val t = new Thread(new Runnable {
      override def run(): Unit = {
        Http().bindAndHandle(routes, serverAddress, serverPort)
        log.info("routes initialized")
      }
    })
    t.start()
  }

  // to be handled in other SGBplus parts

  def handleLoginRequest(body: String): ToResponseMarshallable = {
    "stub for login"
  }

  def handleTokenRequest(): ToResponseMarshallable = {
    "stub for CSRF token"
  }

  def handleLogoutRequest(body: String): ToResponseMarshallable = {
    "stub for logout"
  }
}