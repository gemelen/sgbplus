package net.gemelen.dev.sgbplus.gate

import akka.actor.{ActorSystem, Props}
import akka.event.slf4j.SLF4JLogging
import com.typesafe.config.ConfigFactory
import net.gemelen.dev.sgbplus.protocol.AdminActorProtocol.{InitRoutes, ShutDown}

class Main {

}

object Main extends SLF4JLogging {

  val config = ConfigFactory.load()
  val serverAddress = config.getString("akka.http.address")
  val serverPort = config.getInt("akka.http.port")
  implicit val system: ActorSystem = ActorSystem(config.getString("akka.systemName"), config.getConfig("akka"))

  def main(args: Array[String]): Unit = {
    log.info("Starting up")
    val adminActor = system.actorOf(Props(new AdminActor(serverAddress, serverPort, system)))
    adminActor ! InitRoutes

    scala.sys.addShutdownHook {
      adminActor ! ShutDown
    }
  }
}