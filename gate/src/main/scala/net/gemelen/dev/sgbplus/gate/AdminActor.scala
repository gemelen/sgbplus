package net.gemelen.dev.sgbplus.gate

import akka.actor.{Actor, ActorSystem, Props}
import akka.event.slf4j.SLF4JLogging
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.pattern.ask
import akka.stream.ActorMaterializer
import net.gemelen.dev.sgbplus.actors.PlayerManagerActor
import net.gemelen.dev.sgbplus.protocol.AdminActorProtocol.{InitRoutes, ShutDown}
import net.gemelen.dev.sgbplus.protocol.PlayerManagerProtocol.Get

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

/**
  * Handles actor system start/stop requests
  */
class AdminActor(serverAddress: String, serverPort: Integer, implicit val system: ActorSystem) extends Actor with SLF4JLogging {

  implicit val executionContext = system.dispatcher
  implicit val actorMaterializer = ActorMaterializer()

  var bindingFuture: Future[ServerBinding] = _
  val playerManager = context.actorOf(Props[PlayerManagerActor], "playerManager")

  def fetchPlayer(playerId: Long): Future[Any] = {
    ask(playerManager, Get(playerId)) (5000 millis)
  }

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
        } ~
        pathPrefix("player" / LongNumber) {
          id =>
            val maybePlayer: Future[Any] = fetchPlayer(id)

            onSuccess(maybePlayer) {
              case Some(item) => complete(item.toString)
              case None => complete(StatusCodes.NotFound.defaultMessage)
            }
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
  }

}
