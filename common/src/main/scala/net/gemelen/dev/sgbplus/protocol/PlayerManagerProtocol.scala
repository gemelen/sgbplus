package net.gemelen.dev.sgbplus.protocol

/**
  * Commands for player manager
  */
object PlayerManagerProtocol {
  case object Initialize

  case class Create(email: String, password: String, nickname: Option[String])
  case class Remove(id: Long)
  case class Update(id: Long, email: String, password: String, nickname: Option[String])
  case class Get(id: Long)
}
