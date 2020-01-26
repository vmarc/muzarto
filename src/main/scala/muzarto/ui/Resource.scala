package muzarto.ui

import scalafx.scene.image.Image

class Resource()

object Resource {
  def image(name: String) = {
    println(classOf[Resource].getResource("."))
    new Image(classOf[Resource].getResource(s"/$name").toString)
  }
}
