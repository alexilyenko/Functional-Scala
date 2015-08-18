package week2

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner


@RunWith(classOf[JUnitRunner])
class FunctionalSetSuite extends FunSuite {

  import FunctionalSet._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s4 = (elem: Int) => (1 to 12).contains(elem)
    val s5 = (elem: Int) => (8 to 20).contains(elem)
    val s6 = (elem: Int) => (1001 to 1003).contains(elem)
    val s7 = (elem: Int) => (-1001 to -999).contains(elem)
  }

  test("singletonSet of 1 contains 1") {
    new TestSets {
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements from both sets") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("intersect contains only intersecting elements from both sets") {
    new TestSets {
      val s = intersect(s4, s5)
      assert(contains(s, 9), "Intersect 1")
      assert(contains(s, 8), "Intersect 2")
      assert(contains(s, 12), "Intersect 3")
      assert(!contains(s, 14), "Intersect 4")
      assert(!contains(s, 5), "Intersect 5")
    }
  }


  test("diff contains only elements in first set and not in second one") {
    new TestSets {
      val s = diff(s4, s5)
      assert(!contains(s, 9), "Difference 1")
      assert(!contains(s, 8), "Difference 2")
      assert(!contains(s, 12), "Difference 3")
      assert(!contains(s, 14), "Difference 4")
      assert(contains(s, 5), "Difference 5")
    }
  }

  test("filter contains only elements which satisfies both functions") {
    new TestSets {
      val s = filter(s4, x => x % 2 == 0)
      assert(!contains(s, -4), "Filter 1")
      assert(contains(s, 8), "Filter 2")
      assert(contains(s, 12), "Filter 3")
      assert(!contains(s, 14), "Filter 4")
      assert(!contains(s, 5), "Filter 5")
    }
  }

  test("forAll returns true if all elements in the given set satisfy given function") {
    new TestSets {
      assert(forall(s4, x => x >= 1 && x <= 12), "ForAll 1")
      assert(!forall(s4, x => x < 5), "ForAll 2")
      assert(!forall(s4, x => x % 2 == 0), "ForAll 3")
      assert(forall(s4, x => x > 0), "ForAll 4")
      assert(!forall(s4, x => x > 11), "ForAll 5")
    }
  }

  test("exists returns true if any number in set satisfies second function") {
    new TestSets {
      assert(exists(s4, x => x > 6), "Exists 1")
      assert(!exists(s4, x => x > 12), "Exists 2")
      assert(exists(s4, x => x == 12), "Exists 3")
      assert(exists(s4, x => x == 1), "Exists 4")
      assert(!exists(s4, x => x < 0), "Exists 5")
      assert(!exists(s6, x => x > 0), "Exists 6")
      assert(!exists(s7, x => x > 0), "Exists 7")
      assert(exists(s7, x => x < 0), "Exists 8")
    }
  }

  test("map returns a set transformed by applying given function to a given set") {
    new TestSets {
      val set = map(elem => (1 to 5).contains(elem), x => x * 2)
      assert(contains(set, 2), "Map 1")
      assert(contains(set, 4), "Map 2")
      assert(!contains(set, 5), "Map 3")
      assert(contains(set, 6), "Map 4")
      assert(contains(set, 10), "Map 5")
      assert(!contains(set, 1), "Map 6")
      assert(!contains(set, 3), "Map 6")
      assert(!contains(set, -5), "Map 7")
      assert(!contains(set, 11), "Map 8")
    }
  }
}
