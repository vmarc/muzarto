package muzarto.ui

object Position {

  def format(millis: Double): String = {
    val seconds = (millis / 1000).toInt % 60
    val minutes = (millis / (1000 * 60)).toInt
    "%02d:%02d".format(minutes, seconds)
  }

}
