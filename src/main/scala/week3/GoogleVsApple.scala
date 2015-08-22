package week3

import week3.utils.{TweetReader, TweetList}

import scala.language.postfixOps

object GoogleVsApple {
  val google = List("android", "Android", "galaxy", "Galaxy", "nexus", "Nexus")
  val apple = List("ios", "iOS", "iphone", "iPhone", "ipad", "iPad")

  def findTweets(list: List[String]): TweetSet =
    TweetReader.allTweets filter (tweet => list exists (keyWord => tweet.text contains keyWord))

  lazy val googleTweets: TweetSet = findTweets(google)
  lazy val appleTweets: TweetSet = findTweets(apple)

  /**
   * A list of all tweets mentioning a keyword from either apple or google,
   * sorted by the number of retweets.
   */
  lazy val trending: TweetList = googleTweets union appleTweets descendingByRetweet

  object Main extends App {
    // Print the trending tweets
    GoogleVsApple.trending foreach println
  }
}
