package model

import scala.collection.mutable

case class Votes(up: mutable.Set[User] = mutable.Set.empty, down: mutable.Set[User] = mutable.Set.empty)

case class Queue(playing: Option[QueueItem], queue: List[QueueItem])

case class QueueItem(id: String, track: Track, by: User, votes: Votes = Votes()) {
  def shouldSkip: Boolean = votes.down.size > votes.up.size
}


trait Event
trait ItemEvent extends Event
trait PlaybackEvent extends Event

case class ErrorMsg(msg: String) extends Event
case class LeaveJoin(users: Set[User], listeners: Set[User], lurkers: Int) extends Event
case class Chat(msg: String, who: User) extends Event

case class ItemAdded(item: QueueItem) extends ItemEvent
case class ItemUpdated(item: QueueItem) extends ItemEvent
case class ItemMoved(id: String, nowBefore: String) extends ItemEvent
case class ItemSkipped(id: String) extends ItemEvent

case class PlaybackStarted(item: QueueItem) extends PlaybackEvent
case class PlaybackProgress(pos: Double, ts: Long) extends PlaybackEvent
case class PlaybackSkipped(id: String) extends PlaybackEvent
case object PlaybackFinished extends PlaybackEvent
case object StartBroadcasting extends PlaybackEvent
case object StopBroadcasting extends PlaybackEvent