package net.gemelen.dev.sgbplus.actors

import akka.actor.Actor
import akka.event.slf4j.SLF4JLogging
import net.gemelen.dev.sgbplus.domain.Player
import net.gemelen.dev.sgbplus.protocol.PlayerManagerProtocol._

import scala.collection.mutable

/**
  * Player manager stub
  */
class PlayerManagerActor extends Actor with SLF4JLogging {

  val repo: scala.collection.mutable.HashMap[Long, Player] = new mutable.HashMap[Long, Player]()

  override def preStart(): Unit = {
    log.info("initializing player manager")
    repo.put(1L, new Player("example@mail.com", "hashed", Some("chocolate_bear")))
    repo.put(2L, new Player("another@mail.com", "plaintext", Some("kishore_gopalan")))
  }

  def getPlayer(id: Long): Option[Player] = {
    log.info(s"getting player $id")
    repo.get(id)
  }

  override def receive: Receive = {
    case Get(id) => sender() ! getPlayer(id)
    case _ => log.warn("unknown command")
  }
}
