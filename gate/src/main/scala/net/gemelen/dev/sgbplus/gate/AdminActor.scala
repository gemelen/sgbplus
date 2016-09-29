package net.gemelen.dev.sgbplus.gate

import akka.actor.{Actor, ActorSystem}
import akka.event.slf4j.SLF4JLogging
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import net.gemelen.dev.sgbplus.protocol.AdminActorProtocol.{InitRoutes, ShutDown}

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

/**
  * Handles actor system start/stop requests
  */
class AdminActor(serverAddress: String, serverPort: Integer, implicit val system: ActorSystem) extends Actor with SLF4JLogging {

  implicit val executionContext = system.dispatcher
  implicit val materializer = ActorMaterializer()

  var bindingFuture: Future[ServerBinding] = _

  def initRoutes(): Unit = {
    log.info("Initializing routes")
    val routes =
      get {
        path("hello") {
          complete("hello from sgb")
        } ~
          path("goodbye") {
            self ! ShutDown
            complete("shutting sgb down")
          }
      }

    bindingFuture = Http().bindAndHandle(routes, serverAddress, serverPort)
  }

  def shutdownSystem(): Unit = {
    log.info("Shutting down")
    bindingFuture.flatMap(_.unbind()).onComplete(_ => system.terminate())
    Await.result(system.whenTerminated, 5 seconds)
  }

  override def receive: Receive = {
    case InitRoutes => initRoutes()
    case ShutDown => shutdownSystem()
    case _ => log.warn("unknown command")
  }

}
