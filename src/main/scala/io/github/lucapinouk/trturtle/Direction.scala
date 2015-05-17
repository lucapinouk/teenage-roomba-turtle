package io.github.lucapinouk.trturtle

sealed trait Direction {
  /* Function move updates the given coordinates based on the direction */
  val move: (Int, Int) => (Int, Int)
}
case class North() extends Direction {
  val move: (Int, Int) => (Int, Int) = (x, y) => (x, y + 1) }
case class South() extends Direction {
  val move: (Int, Int) => (Int, Int) = (x, y) => (x, y - 1) }
case class West() extends Direction {
  val move: (Int, Int) => (Int, Int) = (x, y) => (x - 1, y) }
case class East() extends Direction {
  val move: (Int, Int) => (Int, Int) = (x, y) => (x + 1, y) }

