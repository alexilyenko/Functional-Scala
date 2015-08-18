package week1

import scala.annotation.tailrec
import scala.language.postfixOps

object RecursiveFunctions {

  /**
   * The following pattern of numbers is called Pascal’s triangle.
   *     1
   *    1 1
   *   1 2 1
   *  1 3 3 1
   * 1 4 6 4 1
   *
   * The numbers at the edge of the triangle are all 1, and each number inside the triangle is the sum of the
   * two numbers above it. The function computes the elements of Pascal’s triangle by means of a recursive process.
   *
   * Function takes a column c and a row r, counting from 0 and returns the number at that spot in the triangle.
   * For example, pascal(0,2)=1, pascal(1,2)=2 and pascal(1,3)=3.
   */
  def pascal(c: Int, r: Int): Int = {
    if (c > r || c < 0 || r < 0) 0
    else if (c == 0) 1
    else pascal(c - 1, r - 1) + pascal(c, r - 1)
  }

  /**
   * Recursive function which verifies the balancing of parentheses in a string represented by List of Chars
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
   * Recursive function that counts how many different ways you can make change for an amount, given a list of
   * coin denominations. For example, there are 3 ways to give change for 4 if you have coins with denomination 1
   * and 2: 1+1+1+1, 1+1+2, 2+2.
   *
   * Function takes an amount to change, and a list of unique denominations for the coins
   */
  def countChange(money: Int, coins: List[Int]): Int =
    if (money == 0) 1
    else if (money < 0) 0
    else if (coins.isEmpty) 0
    else countChange(money - coins.head, coins) + countChange(money, coins.tail)

}
