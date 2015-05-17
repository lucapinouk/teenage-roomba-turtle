# Teenage Roomba Turtle

Teenage Roomba Turtle is a command line simulation of autonomous hoovers (like [Roomba](http://en.wikipedia.org/wiki/Roomba)) written in Scala.

The simulation can be seen as a mutation of old [turtle](http://en.wikipedia.org/wiki/Turtle_graphics) programs, that instead of writing on a surface it erases the traces already present, hence the name. It is also similar to a [Snake](http://en.wikipedia.org/wiki/Snake_\(video_game\)) that does not grow, but Teenage Roomba Turtle is way cooler than Immature Snake.

## Compile

The best way to compile the project is by using [Apache Maven](https://maven.apache.org/). Once you have Maven configured properly, go to your Teenage Roomba Turtle folder and run `mvn package`: 

```bash
$ cd teenage-roomba-turtles
$ mvn package
```

## Usage

After having compiled the project you can run Teenage Roomba Turtle by using the convenience shell script `teenage-roomba-turtle.sh` from a terminal. The program accepts one argument, the position of the input file. If the argument is not provided, it is assumed that the file is `input.txt` of the current directory.

> If you’re on *nix, make sure that the script is executable (otherwise do a chmod 755 teenage-roomba-turtle.sh).

```bash
$ ./teenage-roomba-turtle.sh input2.txt
1 4
0
$ ./teenage-roomba-turtle.sh
1 3
1
```

You can also directly use `java -jar target/teenage-roomba-turtles-1.0.jar` to run the application:

```bash
$ java -jar target/teenage-roomba-turtles-1.0.jar input2.txt
1 4
0
$ java -jar target/teenage-roomba-turtles-1.0.jar
1 3
1
```

## Documentation

The tool reads inputs regarding the initial situation of the room, the hoover and the directions from a file (default: `input.txt`) and prints in the standard output the final status of the hoover after completing the movements. The hoover cleans up each spot it passes through, and keeps track of the number of dirt spots it cleaned up.

### Input
Each line of the input is a different instruction for the tool:

* the first line contains the coordinates representing the room size
* the second line contains the coordinates representing the initial position of the hoover
* the subsequent (zero or more) lines are a list of coordinates of dirt spots in the room
* the last line contains the sequence of directions, given as initials of cardinal points, that the hoover will take.

In particular the coordinates (x, y) are represented as a couple of numbers separated by a whitespace `x y`.
The given coordinates for the hoover and the dirt spots must be room valid spots ranging from 0 to (size-1), where (0, 0) represents the South-West corner of the room.

Example:

```
5 5
1 2
1 0
2 2
2 3
NNESEESWNWW
```

This example represents the following initial situation:

<table>
    <tr>
      <th>4</th><td></td><td></td><td></td><td></td><td></td>
    </tr>
    <tr>
      <th>3</th><td></td><td></td><td>X</td><td></td><td></td>
    </tr>
    <tr>
      <th>2</th><td></td><td>O</td><td>X</td><td></td><td></td>
    </tr>
    <tr>
      <th>1</th><td></td><td></td><td></td><td></td><td></td>
    </tr>
    <tr>
      <th>0</th><td></td><td>X</td><td></td><td></td><td></td>
    </tr>
    <tr>
      <th>SW</th><th>0</th><th>1</th><th>2</th><th>3</th><th>4</th>
    </tr>
</table>

Where X represent dirt spots and O represent the hoover position.

### Output

The output is sent to the Standard Output, and it is composed by two lines:

* the first line contains the coordinates representing the hoover final position (after executing the sequence of directions)
* the second line contains the number of spots that have been cleaned by the hoover.

Example (matching the input above):

```
1 3
1
```

In fact the final situation is:

<table>
    <tr>
      <th>4</th><td></td><td></td><td></td><td></td><td></td>
    </tr>
    <tr>
      <th>3</th><td></td><td>O</td><td>~</td><td></td><td></td>
    </tr>
    <tr>
      <th>2</th><td></td><td></td><td>X</td><td></td><td></td>
    </tr>
    <tr>
      <th>1</th><td></td><td></td><td></td><td></td><td></td>
    </tr>
    <tr>
      <th>0</th><td></td><td>X</td><td></td><td></td><td></td>
    </tr>
    <tr>
      <th>SW</th><th>0</th><th>1</th><th>2</th><th>3</th><th>4</th>
    </tr>
</table>

Where ~ represents a spot that has been cleaned.

### Behaviour

The hoover is running since the beginning of the program, so if the hoover is initially positioned on a dirt spot the spot gets cleaned before moving to the next direction. If a direction would cause the robot to move against a wall, the robot skips that direction and goes on with the next one.

The tool quits before completion while returning and appropriate error in the Standard Error if the instructions are in the wrong format or conflicting (e.g., the hoover or the dirt spots are positioned outside the boundaries) or if the arguments passed are invalid.

## Licence

This software is licensed under the [MIT license](http://opensource.org/licenses/MIT).
