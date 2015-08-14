package assignment1

import scala.annotation.tailrec
import scala.language.postfixOps

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    if (c > r || c < 0 || r < 0) 0
    else if (c == 0) 1
    else pascal(c - 1, r - 1) + pascal(c, r - 1)
  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {

    @tailrec
    def parenthesesSearch(rest: List[Char], notClosed: Int): Boolean =
      if (notClosed < 0) false
      else if (rest.isEmpty) notClosed == 0
      else parenthesesSearch(rest.tail, notClosed + balanceChange(rest.head))


    def balanceChange(ch: Char) = if (ch == ')') -1 else if (ch == '(') 1 else 0

    parenthesesSearch(chars, 0)
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int =
    if (money == 0) 1
    else if (money < 0) 0
    else if (coins.isEmpty) 0
    else countChange(money - coins.head, coins) + countChange(money, coins.tail)

}
