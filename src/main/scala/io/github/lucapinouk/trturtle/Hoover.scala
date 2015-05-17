package io.github.lucapinouk.trturtle


object Hoover {
  /* Creates a new Hoover in the given position,
     and starts it up so it cleans on the spot */
  def initialPosition(position: (Int, Int), dirtSpots: Set[(Int, Int)]) =
    HooverImpl(position, 0).cleanSpot(dirtSpots)
}

/* The Hoover keeps track of the hoover position
   and the number of spots that have been cleared */
trait Hoover {
  val position: (Int, Int)
  val cleanedSpots: Int

  /* Updates the position and the cleanedSpots */
  def moveAndClean(newPosition: (Int, Int), dirtSpots: Set[(Int, Int)]): Hoover
}

/* Hidden standard implementation of the Hoover,
   allowing injection of different hoovers */
case class HooverImpl(position: (Int, Int), cleanedSpots: Int) extends Hoover{
  /* Updates the cleanedSpots based on the current position and dirty spots */
  def cleanSpot(dirtSpots: Set[(Int, Int)]): Hoover ={
    if(dirtSpots.contains(position)) HooverImpl(position, cleanedSpots + 1)
    else this
  }

  /* Updates the position and the cleanedSpots */
  def moveAndClean(newPosition: (Int, Int), dirtSpots: Set[(Int, Int)]): Hoover ={
    HooverImpl(newPosition, cleanedSpots).cleanSpot(dirtSpots)
  }
}