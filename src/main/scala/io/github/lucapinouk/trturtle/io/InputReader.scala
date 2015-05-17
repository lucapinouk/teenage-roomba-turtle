package io.github.lucapinouk.trturtle.io

import io.github.lucapinouk.trturtle._

import scala.annotation.tailrec
import scala.io.Source

/* Reads the program inputs */
object InputReader {
  /* Reads and parses the program inputs from the given Source (can be a file or a string) */
  def read(source: Source): (RoomStatus, Seq[Direction])  = {
    val lines = source.getLines()

    if(!lines.hasNext) throw new ParsingException("Error: room size not specified")
    val roomSize = CoordParser.parseCoord(lines.next())
      .getOrElse(throw new ParsingException("Error parsing the room size"))

    if(!lines.hasNext) throw new ParsingException("Error: hoover position not specified")
    val hooverPos = CoordParser.parseCoordInBoundary(lines.next(), roomSize)
      .getOrElse(throw new ParsingException("Error parsing the hoover position"))

    val(dirtSpots, nextLine) = readDirtSpots(lines, roomSize)
//    if(lines.hasNext) throw new ParsingException("Too many lines")
    val directions = DirectionParser.parseDirections(nextLine)

    (RoomStatus.initialRoomStatus(roomSize, dirtSpots, hooverPos), directions)
  }


  /* Reads the dirt spots from the given lines and returns
     the parsed dirt spots and the last line that could not be parsed */
  def readDirtSpots(lines: Iterator[String], boundaries: (Int, Int)): (Set[(Int, Int)], String) = {
    /* store the last line read, as the last one
       that could not be parsed as dirt coordinates
       should be used to read the directions */
    var line = ""

    @tailrec
    def readDirtSpotsAcc(lines: Iterator[String], dirtSpots: Set[(Int, Int)]):Set[(Int, Int)] = {
      if(!lines.hasNext) throw new ParsingException("Error: no directions specified")

      line = lines.next()
      CoordParser.parseCoordInBoundary(line, boundaries) match {
        case CoordParser.Success(parsedCoord, _) =>
          readDirtSpotsAcc(lines, dirtSpots + parsedCoord)

        /* if the coordinates are not parsed correctly,
           then the dirt spots are completed and can be returned */
        case CoordParser.Failure(msg, _) => dirtSpots
        case CoordParser.Error(msg, _) => dirtSpots
      }
    }

    (readDirtSpotsAcc(lines, Set()), line)
  }
}


