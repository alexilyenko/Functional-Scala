package week4

import scala.annotation.tailrec
import scala.language.postfixOps

object Huffman {

  type Bit = Int
  type CodeTable = List[(Char, List[Bit])]
  /**
   * A Huffman coding tree for the French language.
   * Generated from the data given at
   * http://fr.wikipedia.org/wiki/Fr%C3%A9quence_d%27apparition_des_lettres_en_fran%C3%A7ais
   */
  val frenchCode: CodeTree = Fork(Fork(Fork(Leaf('s', 121895), Fork(Leaf('d', 56269), Fork(Fork(Fork(Leaf('x', 5928), Leaf('j', 8351), List('x', 'j'), 14279), Leaf('f', 16351), List('x', 'j', 'f'), 30630), Fork(Fork(Fork(Fork(Leaf('z', 2093), Fork(Leaf('k', 745), Leaf('w', 1747), List('k', 'w'), 2492), List('z', 'k', 'w'), 4585), Leaf('y', 4725), List('z', 'k', 'w', 'y'), 9310), Leaf('h', 11298), List('z', 'k', 'w', 'y', 'h'), 20608), Leaf('q', 20889), List('z', 'k', 'w', 'y', 'h', 'q'), 41497), List('x', 'j', 'f', 'z', 'k', 'w', 'y', 'h', 'q'), 72127), List('d', 'x', 'j', 'f', 'z', 'k', 'w', 'y', 'h', 'q'), 128396), List('s', 'd', 'x', 'j', 'f', 'z', 'k', 'w', 'y', 'h', 'q'), 250291), Fork(Fork(Leaf('o', 82762), Leaf('l', 83668), List('o', 'l'), 166430), Fork(Fork(Leaf('m', 45521), Leaf('p', 46335), List('m', 'p'), 91856), Leaf('u', 96785), List('m', 'p', 'u'), 188641), List('o', 'l', 'm', 'p', 'u'), 355071), List('s', 'd', 'x', 'j', 'f', 'z', 'k', 'w', 'y', 'h', 'q', 'o', 'l', 'm', 'p', 'u'), 605362), Fork(Fork(Fork(Leaf('r', 100500), Fork(Leaf('c', 50003), Fork(Leaf('v', 24975), Fork(Leaf('g', 13288), Leaf('b', 13822), List('g', 'b'), 27110), List('v', 'g', 'b'), 52085), List('c', 'v', 'g', 'b'), 102088), List('r', 'c', 'v', 'g', 'b'), 202588), Fork(Leaf('n', 108812), Leaf('t', 111103), List('n', 't'), 219915), List('r', 'c', 'v', 'g', 'b', 'n', 't'), 422503), Fork(Leaf('e', 225947), Fork(Leaf('i', 115465), Leaf('a', 117110), List('i', 'a'), 232575), List('e', 'i', 'a'), 458522), List('r', 'c', 'v', 'g', 'b', 'n', 't', 'e', 'i', 'a'), 881025), List('s', 'd', 'x', 'j', 'f', 'z', 'k', 'w', 'y', 'h', 'q', 'o', 'l', 'm', 'p', 'u', 'r', 'c', 'v', 'g', 'b', 'n', 't', 'e', 'i', 'a'), 1486387)
  /**
   * Decoded secret message
   */
  val secret: List[Bit] = List(0, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1)

  def weight(tree: CodeTree): Int = tree match {
    case Leaf(_, w) => w
    case Fork(_, _, _, w) => w
  }

  def chars(tree: CodeTree): List[Char] = tree match {
    case Leaf(ch, _) => List(ch)
    case Fork(l, r, _, _) => chars(l) ::: chars(r)

  }

  def makeCodeTree(left: CodeTree, right: CodeTree) =
    Fork(left, right, chars(left) ::: chars(right), weight(left) + weight(right))

  /** Creates a character list from a given string. */
  def string2Chars(str: String): List[Char] = str toList

  /**
   * This function computes for each unique character in the list `chars` the number of
   * times it occurs. For example, the invocation
   *
   * times(List('a', 'b', 'a'))
   *
   * should return the following (the order of the resulting list is not important):
   *
   * List(('a', 2), ('b', 1))
   *
   * The type `List[(Char, Int)]` denotes a list of pairs, where each pair consists of a
   * character and an integer.
   *
   */
  def times(chars: List[Char]): List[(Char, Int)] = chars groupBy identity mapValues (_.size) toList

  /**
   * Returns a list of `Leaf` nodes for a given frequency table `freqs`.
   *
   * The returned list is ordered by ascending weights, where the weight
   * of a leaf is the frequency of the character.
   */
  def makeOrderedLeafList(freqs: List[(Char, Int)]): List[Leaf] = freqs match {
    case List() => List()
    case head :: tail => Leaf(head._1, head._2) :: makeOrderedLeafList(tail) sortWith (_.weight < _.weight)
  }

  /**
   * Checks whether the list `trees` contains only one single code tree.
   */
  def singleton(trees: List[CodeTree]): Boolean = trees.size == 1

  /**
   * The parameter `trees` of this function is a list of code trees ordered
   * by ascending weights.
   *
   * This function takes the first two elements of the list `trees` and combines
   * them into a single `Fork` node. This node is then added back into the
   * remaining elements of `trees` at a position such that the ordering by weights
   * is preserved.
   *
   * If `trees` is a list of less than two elements, that list should be returned
   * unchanged.
   */
  def combine(trees: List[CodeTree]): List[CodeTree] =
    if (trees.size < 2) trees
    else makeCodeTree(trees head, trees.tail head) :: trees.tail.tail sortWith (weight(_) < weight(_))

  /**
   * Calls the two functions until the list of code trees contains only one single tree, and then return
   * that singleton list.
   */
  @tailrec
  def until(singleton: List[CodeTree] => Boolean, combine: List[CodeTree] => List[CodeTree])(trees: List[CodeTree]): List[CodeTree] =
    if (singleton(trees)) trees
    else until(singleton, combine)(combine(trees))

  /**
   * This function creates a code tree which is optimal to encode the text `chars`.
   *
   * The parameter `chars` is an arbitrary text. This function extracts the character
   * frequencies from that text and creates a code tree based on them.
   */
  def createCodeTree(chars: List[Char]): CodeTree = until(singleton, combine)(makeOrderedLeafList(times(chars))) head

  /**
   * Function that returns the decoded secret
   */
  def decodedSecret: List[Char] = decode(frenchCode, secret)

  /**
   * This function decodes the bit sequence `bits` using the code tree `tree` and returns
   * the resulting list of characters.
   */
  def decode(tree: CodeTree, bits: List[Bit]): List[Char] = {
    def traverse(t: CodeTree, b: List[Bit]): List[Char] = t match {
      case Leaf(ch, _) => if (b.isEmpty) List(ch) else ch :: traverse(tree, b)
      case Fork(l, r, _, _) => if (b.head == 0) traverse(l, b.tail) else traverse(r, b.tail)
    }
    traverse(tree, bits)
  }

  /**
   * This function encodes `text` using the code tree `tree`
   * into a sequence of bits.
   */
  def encode(tree: CodeTree)(text: List[Char]): List[Bit] = {
    def lookup(t: CodeTree)(ch: Char): List[Bit] = t match {
      case Leaf(_, _) => List()
      case Fork(l, r, _, _) => if (chars(l).contains(ch)) 0 :: lookup(l)(ch) else 1 :: lookup(r)(ch)
    }
    text flatMap lookup(tree)
  }

  /**
   * This function returns the bit sequence that represents the character `char` in
   * the code table `table`.
   */
  def codeBits(table: CodeTable)(char: Char): List[Bit] = table.find(c => c._1 == char).get._2

  /**
   * Creates a code table which contains, for every character in the
   * code tree, the sequence of bits representing that character.
   */
  def convert(tree: CodeTree): CodeTable = {
    tree match {
      case Leaf(ch, _) => List((ch, List()))
      case Fork(l, r, _, _) => mergeCodeTables(convert(l), convert(r))
    }
  }

  /**
   * This function takes two code tables and merges them into one
   */
  def mergeCodeTables(a: CodeTable, b: CodeTable): CodeTable = {
    def prepend(bit: Bit)(codeTable: CodeTable): CodeTable = codeTable map (code => (code._1, bit :: code._2))
    prepend(0)(a) ::: prepend(1)(b)
  }

  /**
   * This function encodes `text` according to the code tree `tree`.
   *
   * To speed up the encoding process, it first converts the code tree to a code table
   * and then uses it to perform the actual encoding.
   */
  def quickEncode(tree: CodeTree)(text: List[Char]): List[Bit] = text flatMap codeBits(convert(tree))

  /**
   * A huffman code is represented by a binary tree.
   *
   * Every `Leaf` node of the tree represents one character of the alphabet that the tree can encode.
   * The weight of a `Leaf` is the frequency of appearance of the character.
   *
   * The branches of the huffman tree, the `Fork` nodes, represent a set containing all the characters
   * present in the leaves below it. The weight of a `Fork` node is the sum of the weights of these
   * leaves.
   */
  abstract class CodeTree

  case class Fork(left: CodeTree, right: CodeTree, chars: List[Char], weight: Int) extends CodeTree

  case class Leaf(char: Char, weight: Int) extends CodeTree
}
