package week5_6

package object ForCompiler {
  val dictionaryPath = List("resources", "linuxwords.txt")

  def loadDictionary = {
    val wordStream = Option {
      getClass.getClassLoader.getResourceAsStream(dictionaryPath.mkString("/"))
    } orElse {
      Common.resourceAsStreamFromSrc(dictionaryPath)
    } getOrElse {
      sys.error("Could not load word list, dictionary file not found")
    }
    try {
      val s = io.Source.fromInputStream(wordStream)
      s.getLines().toList
    } catch {
      case e: Exception =>
        println("Could not load word list: " + e)
        throw e
    } finally {
      wordStream.close()
    }
  }

}
