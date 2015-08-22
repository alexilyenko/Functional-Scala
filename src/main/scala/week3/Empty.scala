package week3

import week3.utils.{Nil, Tweet, TweetList}

class Empty extends TweetSet {

  override def filterAcc(p: Tweet => Boolean, acc: TweetSet): TweetSet = acc

  override def contains(tweet: Tweet): Boolean = false

  override def incl(tweet: Tweet): TweetSet = new NonEmpty(tweet, new Empty, new Empty)

  override def remove(tweet: Tweet): TweetSet = this

  override def foreach(f: Tweet => Unit): Unit = ()

  /**
   * Returns a new `TweetSet` that is the union of `TweetSet`s `this` and `that`.
   */
  override def union(that: TweetSet): TweetSet = that

  /**
   * Returns the tweet from this set which has the greatest retweet count.
   * Calling `mostRetweeted` on an empty set throws an exception of
   * type `java.util.NoSuchElementException`.
   */
  override def mostRetweeted: Tweet = throw new NoSuchElementException

  /**
   * Returns a list containing all tweets of this set, sorted by retweet count
   * in descending order. In other words, the head of the resulting list should
   * have the highest retweet count.
   */
  override def descendingByRetweet: TweetList = Nil
}

