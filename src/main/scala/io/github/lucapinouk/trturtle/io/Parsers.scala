package io.github.lucapinouk.trturtle.io

import io.github.lucapinouk.trturtle._

import scala.util.parsing.combinator._

/* Exception used to report errors occurred during the parsing */
case class ParsingException(message: String) extends Exception(message)

/* Parser for the directions */
object DirectionParser {

  /* Parses a character 'N' | 'S' | 'W' | 'E' into a Direction */
  def parseDirection(charDirection: Char): Direction = {
    charDirection match {
      case 'N' => new North()
      case 'S' => new South()
      case 'W' => new West()
      case 'E' => new East()
      case _ => throw new ParsingException("Error parsing the directions")
    }

  }

  /* Parses a string containing directions into a sequence of Direction */
  def parseDirections(stringDirections: String): Seq[Direction] = {
    val directions: Seq[Direction] = stringDirections map parseDirection

    if (directions.isEmpty)
      throw new ParsingException("Error: no directions specified")

    directions
  }
}

/* Parser for coordinates X, Y */
object CoordParser extends RegexParsers {
  def numberParser: Parser[Int]    = """(0|[1-9]\d*)""".r ^^ { _.toInt }
  def numberEndParser: Parser[Int]    = """(0|[1-9]\d*)\z""".r ^^ { _.toInt }
  def coordParser: Parser[(Int, Int)] = numberParser ~ numberEndParser  ^^ { case x ~ y  => (x, y) }


  /* Parses a string containing only coordinates X, Y separated by a whitespace */
  def parseCoord(text: String) = { parse[(Int, Int)](coordParser, text) }

  /* Parses a string containing only coordinates X, Y separated by a whitespace
     and checking that the coordinates are within a given boundary */
  def parseCoordInBoundary(stringCoord: String, boundaries: (Int, Int)): ParseResult[(Int, Int)] = {
    val coord = parseCoord(stringCoord)

    if(coord.successful && (coord.get._1 >= boundaries._1 || coord.get._2 >= boundaries._2)){
      return Error("Out of boundaries", coord.next)
    }

    coord
  }
}