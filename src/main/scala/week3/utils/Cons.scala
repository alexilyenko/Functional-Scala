package week3.utils

class Cons(val head: Tweet, val tail: TweetList) extends TweetList {
  def isEmpty = false
}
