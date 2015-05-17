package io.github.lucapinouk.trturtle

import scala.annotation.tailrec

object RoomStatus{
  /* Creates a new RoomStatus with the given size and dirtSpots,
     and positions and starts up the hoover, so it cleans on the spot */
  def initialRoomStatus(roomSize: (Int, Int), dirtSpots: Set[(Int, Int)], hooverPos: (Int, Int)) =
    RoomStatus(roomSize, dirtSpots - hooverPos, Hoover.initialPosition(hooverPos, dirtSpots))
}

/* The RoomStatus keeps track of the room size,
   the position of the dirt and the Hoover status */
case class RoomStatus(roomSize: (Int, Int), dirtSpots: Set[(Int, Int)], hoover: Hoover){

  /* Moves the hoover over a sequence of directions */
  def moveHoover(directions: Seq[Direction]): RoomStatus = {
    @tailrec
    def moveHooverAcc(roomStatus: RoomStatus, directions: Seq[Direction]): RoomStatus = {
      if (directions.isEmpty) roomStatus
      else moveHooverAcc(roomStatus.moveHoover(directions.head), directions.tail)
    }

    moveHooverAcc(this, directions)
  }

  /* Moves the hoover over a single direction */
  def moveHoover(direction: Direction): RoomStatus = {
    val newHooverPos = direction.move.tupled(hoover.position)

    /* check if the hoover went against a wall:
       if so, nothing should happen, otherwise update the RoomStatus */
    if(newHooverPos._1 < 0 || newHooverPos._1 >= roomSize._1 ||
      newHooverPos._2 < 0 || newHooverPos._2 >= roomSize._2){

      this

    } else RoomStatus(roomSize, dirtSpots - newHooverPos,
        hoover.moveAndClean(newHooverPos, dirtSpots))

  }
}
