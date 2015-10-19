package week7

import scala.language.postfixOps

/**
  * This component implements the solver for the Bloxorz game
  */
trait Solver extends GameDef {

  /**
    * Returns `true` if the block `b` is at the final position
    */
  def done(b: Block): Boolean = b == Block(goal, goal)

  /**
    * This function takes two arguments: the current block `b` and
    * a list of moves `history` that was required to reach the
    * position of `b`.
    *
    * The `head` element of the `history` list is the latest move
    * that was executed, i.e. the last move that was performed for
    * the block to end up at position `b`.
    *
    * The function returns a stream of pairs: the first element of
    * the each pair is a neighboring block, and the second element
    * is the augmented history of moves required to reach this block.
    *
    * It only returns valid neighbors, i.e. block positions
    * that are inside the terrain.
    */
  def neighborsWithHistory(b: Block, history: List[Move]): Stream[(Block, List[Move])] =
    b.legalNeighbors map (n => (n._1, n._2 :: history)) toStream

  /**
    * This function returns the list of neighbors without the block
    * positions that have already been explored. We will use it to
    * make sure that we don't explore circular paths.
    */
  def newNeighborsOnly(neighbors: Stream[(Block, List[Move])], explored: Set[Block]): Stream[(Block, List[Move])] =
    neighbors filterNot (explored contains _._1)

  /**
    * The function `from` returns the stream of all possible paths
    * that can be followed, starting at the `head` of the `initial`
    * stream.
    *
    * The blocks in the stream `initial` are sorted by ascending path
    * length: the block positions with the shortest paths (length of
    * move list) are at the head of the stream.
    *
    * The parameter `explored` is a set of block positions that have
    * been visited before, on the path to any of the blocks in the
    * stream `initial`. When search reaches a block that has already
    * been explored before, that position is not included a
    * second time to avoid cycles.
    *
    * The resulting stream is sorted by ascending path length,
    * i.e. the block positions that can be reached with the fewest
    * amount of moves appears first in the stream.
    *
    * Note: the solution does not look at or compare the lengths
    * of different paths - the implementation naturally
    * constructs the correctly sorted stream.
    */
  def from(initial: Stream[(Block, List[Move])],
           explored: Set[Block]): Stream[(Block, List[Move])] = initial match {
    case (block, list) #:: tail =>
      (block, list) #:: from(tail ++ newNeighborsOnly(neighborsWithHistory(block, list), explored), explored + block)
    case _ => Stream()
  }


  /**
    * The stream of all paths that begin at the starting block.
    */
  lazy val pathsFromStart: Stream[(Block, List[Move])] = from(Stream((startBlock, List())), Set())

  /**
    * Returns a stream of all possible pairs of the goal block along
    * with the history how it was reached.
    */
  lazy val pathsToGoal: Stream[(Block, List[Move])] = pathsFromStart filter (x => done(x._1))

  /**
    * The (or one of the) shortest sequence(s) of moves to reach the
    * goal. If the goal cannot be reached, the empty list is returned.
    *
    * Note: the `head` element of the returned list represents
    * the first move that the player performs from the starting
    * position.
    */
  lazy val solution: List[Move] = pathsToGoal match {
    case head #:: _ => head._2 reverse
    case _ => List()
  }
}
