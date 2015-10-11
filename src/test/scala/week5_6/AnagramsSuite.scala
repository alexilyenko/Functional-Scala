package week5_6

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class AnagramsSuite extends FunSuite {
  import Anagrams._

  test("wordOccurrences: abcd") {
    assert(wordOccurrences("abcd") === List(('a', 1), ('b', 1), ('c', 1), ('d', 1)))
  }

  test("wordOccurrences: Robert") {
    assert(wordOccurrences("Robert") === List(('b', 1), ('e', 1), ('o', 1), ('r', 2), ('t', 1)))
  }

  test("sentenceOccurrences: abcd e") {
    assert(sentenceOccurrences(List("abcd", "e")) === List(('a', 1), ('b', 1), ('c', 1), ('d', 1), ('e', 1)))
  }

  test("sentenceOccurrences: I love you") {
    assert(sentenceOccurrences(List("I", "love", "you")) === List(('e', 1), ('i', 1), ('l', 1), ('o', 2), ('u', 1), ('v', 1), ('y', 1)))
  }

  test("dictionaryByOccurrences.get: eat") {
    assert(dictionaryByOccurrences.get(List(('a', 1), ('e', 1), ('t', 1))).map(_.toSet) === Some(Set("ate", "eat", "tea")))
  }

  test("dictionaryByOccurrences.get empty set") {
    assert(dictionaryByOccurrences(List()) === List())
  }

  test("word anagrams: married") {
    assert(wordAnagrams("married").toSet === Set("married", "admirer"))
  }

  test("word anagrams: player") {
    assert(wordAnagrams("player").toSet === Set("parley", "pearly", "player", "replay"))
  }

  test("subtract: lard - r") {
    val lard = List(('a', 1), ('d', 1), ('l', 1), ('r', 1))
    val r = List(('r', 1))
    val lad = List(('a', 1), ('d', 1), ('l', 1))
    assert(subtract(lard, r) === lad)
  }

  test("subtract: aaaaabbbc - aaabc") {
    val aaaaabbbc = List(('a', 5), ('b', 3), ('c', 1))
    val aaabc = List(('a', 3), ('b', 1), ('c', 1))
    val aabb = List(('a', 2), ('b', 2))
    assert(subtract(aaaaabbbc, aaabc) === aabb)
  }

  test("combinations: []") {
    assert(combinations(Nil) === List(Nil))
  }

  test("combinations: abba") {
    val abba = List(('a', 2), ('b', 2))
    val abbacomb = List(
      List(),
      List(('a', 1)),
      List(('a', 2)),
      List(('b', 1)),
      List(('a', 1), ('b', 1)),
      List(('a', 2), ('b', 1)),
      List(('b', 2)),
      List(('a', 1), ('b', 2)),
      List(('a', 2), ('b', 2))
    )
    assert(combinations(abba).toSet === abbacomb.toSet)
  }

  test("combinations: aabbcc") {
    val aabbcc = wordOccurrences("aabbcc")
    val aabbcccomb = List(
      List(),
      List(('c', 1)),
      List(('c', 2)),
      List(('b', 1)),
      List(('b', 1), ('c', 1)),
      List(('b', 1), ('c', 2)),
      List(('b', 2)),
      List(('b', 2), ('c', 1)),
      List(('b', 2), ('c', 2)),
      List(('a', 1)),
      List(('a', 1), ('c', 1)),
      List(('a', 1), ('c', 2)),
      List(('a', 1), ('b', 1)),
      List(('a', 1), ('b', 1), ('c', 1)),
      List(('a', 1), ('b', 1), ('c', 2)),
      List(('a', 1), ('b', 2)),
      List(('a', 1), ('b', 2), ('c', 1)),
      List(('a', 1), ('b', 2), ('c', 2)),
      List(('a', 2)),
      List(('a', 2), ('c', 1)),
      List(('a', 2), ('c', 2)),
      List(('a', 2), ('b', 1)),
      List(('a', 2), ('b', 1), ('c', 1)),
      List(('a', 2), ('b', 1), ('c', 2)),
      List(('a', 2), ('b', 2)),
      List(('a', 2), ('b', 2), ('c', 1)),
      List(('a', 2), ('b', 2), ('c', 2))
    )
    assert(combinations(aabbcc).toSet === aabbcccomb.toSet)
  }

  test("sentence anagrams: []") {
    val sentence = List()
    assert(sentenceAnagrams(sentence) === List(Nil))
  }

  test("sentence occurrence in empty sentence") {
    val sentence = List()
    assert(sentenceOccurrences(sentence) === List())
  }

  test("sentence anagrams: Linux rulez") {
    val sentence = List("Linux", "rulez")
    val anas = List(
      List("Rex", "Lin", "Zulu"),
      List("nil", "Zulu", "Rex"),
      List("Rex", "nil", "Zulu"),
      List("Zulu", "Rex", "Lin"),
      List("null", "Uzi", "Rex"),
      List("Rex", "Zulu", "Lin"),
      List("Uzi", "null", "Rex"),
      List("Rex", "null", "Uzi"),
      List("null", "Rex", "Uzi"),
      List("Lin", "Rex", "Zulu"),
      List("nil", "Rex", "Zulu"),
      List("Rex", "Uzi", "null"),
      List("Rex", "Zulu", "nil"),
      List("Zulu", "Rex", "nil"),
      List("Zulu", "Lin", "Rex"),
      List("Lin", "Zulu", "Rex"),
      List("Uzi", "Rex", "null"),
      List("Zulu", "nil", "Rex"),
      List("rulez", "Linux"),
      List("Linux", "rulez")
    )
    assert(sentenceAnagrams(sentence).toSet === anas.toSet)
  }
}
