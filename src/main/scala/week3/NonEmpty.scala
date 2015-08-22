package week3

import week3.utils.{Cons, TweetList, Tweet}

class NonEmpty(elem: Tweet, left: TweetSet, right: TweetSet) extends TweetSet {

  override def filterAcc(p: Tweet => Boolean, acc: TweetSet): TweetSet =
    left.filterAcc(p, right.filterAcc(p, if (p(elem)) acc.incl(elem) else acc))

  override def contains(x: Tweet): Boolean =
    if (x.text < elem.text) left.contains(x)
    else if (elem.text < x.text) right.contains(x)
    else true

  override def incl(x: Tweet): TweetSet = {
    if (x.text < elem.text) new NonEmpty(elem, left.incl(x), right)
    else if (elem.text < x.text) new NonEmpty(elem, left, right.incl(x))
    else this
  }

  override def remove(tw: Tweet): TweetSet =
    if (tw.text < elem.text) new NonEmpty(elem, left.remove(tw), right)
    else if (elem.text < tw.text) new NonEmpty(elem, left, right.remove(tw))
    else left.union(right)

  override def foreach(f: Tweet => Unit): Unit = {
    f(elem)
    left.foreach(f)
    right.foreach(f)
  }

  /**
   * Returns a new `TweetSet` that is the union of `TweetSet`s `this` and `that`.
   */
  override def union(that: TweetSet): TweetSet = left.union(right.union(that.incl(elem)))

  /**
   * Returns the tweet from this set which has the greatest retweet count.
   */
  override def mostRetweeted: Tweet = mostRetweetedAcc(elem)

  override def mostRetweetedAcc(max: Tweet): Tweet =
    left.mostRetweetedAcc(right.mostRetweetedAcc(if (elem > max) elem else max))

  /**
   * Returns a list containing all tweets of this set, sorted by retweet count
   * in descending order. In other words, the head of the resulting list should
   * have the highest retweet count.
   *
   * Hint: the method `remove` on TweetSet will be very useful.
   * Question: Should we implment this method here, or should it remain abstract
   * and be implemented in the subclasses?
   */
  override def descendingByRetweet: TweetList = new Cons(mostRetweeted, remove(mostRetweeted).descendingByRetweet)
}
