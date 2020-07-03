Functional Programming Principles in Scala
=====================

Code for programming assignments in Scala from the Coursera course, [Functional Programming Principles in Scala](https://www.coursera.org/learn/progfun1).

Week 1 - Functions & Evaluations
--------------------------------
 - **Pascal’s Triangle** - Function that computes the elements of Pascal’s triangle by means of a recursive process
 - **Parentheses Balancing** - Recursive function which verifies the balancing of parentheses in a string
 - **Counting Change** - Recursive function that counts how many different ways you can make change for an amount, given a list of coin denominations

Week 2 - Higher Order Functions
--------------------------------
  - **Functional Set** - Pure functional set data structure with implemented methods such as singletonSet, union, contains, intersect, diff, filter, forAll, exists and map

Week 3 - Data and Abstraction
--------------------------------
  - **Tweet Set** - Object-oriented data structure represented the set of tweets in the form of a binary search tree. Every branch in the tree has two children
  - **NonEmpty Set** - Set which is inherited from Tweet Set and represents non-empty set
  - **Empty Set** - Set which is inherited from Tweet Set and represents an empty set
  - **Google vs Apple Trending** - The simple tweet parser which parses JSON with tweets and produces a list of all tweets mentioning a keyword from either Apple or Google, sorted by the number of retweets

Week 4 - Types and Pattern Matching
--------------------------------
 - **Huffman Coding** - Data structure that implements efficient compression algorithm used to compress lists of characters
 
Weeks 5-6 - Collections
--------------------------------
 - **Anagrams** - Function-based data structure which solves combinatorial problem of finding all the anagrams of a given sentence recursively using the Scala Collections API and for-comprehensions.

Week 7 - Streams & Lazy Evaluation
--------------------------------
 - **Bloxorz** - Scala class represented famous flash game [Bloxorz](http://www.coolmath-games.com/0-bloxorz). Its objective is simple: you must navigate your rectangular block to the hole at the end of the board, by rolling it, in the fewest number of moves possible.
 - **Solver** - Data structure that returns the exact sequence of moves in order to reach the goal position by block and gets the shortest possible way from that sequence
