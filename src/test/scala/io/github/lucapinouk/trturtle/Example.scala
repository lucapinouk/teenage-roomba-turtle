package io.github.lucapinouk.trturtle

trait Example{
  val stringRoomSize = "5 5"
  val stringHooverPos = "1 2"
  val stringMoves = "NNESEESWNWW"
  val stringDirtSpotsAndMoves = "1 0\n2 2\n2 3\n" + stringMoves
  val stringInput = stringRoomSize + "\n" + stringHooverPos + "\n" + stringDirtSpotsAndMoves
  val stringOutput = "1 3\n1"

  val listDirtSpots = "1 0"::"2 2"::"2 3"::stringMoves::Nil

  val objectRoomSize = (5,5)
  val objectHooverPos = (1,2)
  val objectDirtSpots = Set((1,0),(2,2),(2,3))
  val objectDirections =
    North()::North()::East()::South()::East()::East()::South()::West()::North()::West()::West()::Nil
  val inputRoomStatus = RoomStatus(objectRoomSize, objectDirtSpots, HooverImpl(objectHooverPos, 0))
  val outputRoomStatus = RoomStatus(objectRoomSize, objectDirtSpots - ((2,3)), HooverImpl((1, 3), 1))

  val full3x3Set = Set((0,0),(0,1),(0,2),(1,0),(1,1),(1,2),(2,0),(2,1),(2,2))
}
