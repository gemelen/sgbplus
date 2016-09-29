package net.gemelen.dev.sgbplus.domain

class Player(email: String, password: String, nickname: Option[String]) {
  override def toString: String = {
    s"$email $nickname"
  }
}
