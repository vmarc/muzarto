package muzarto.ui

import muzarto.model.MediaPlayerModel

import scalafx.Includes._
import scalafx.application.Platform
import scalafx.beans.property.ObjectProperty
import scalafx.beans.property.StringProperty
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.layout.BorderPane
import scalafx.scene.media.MediaView
import scalafx.stage.Stage

class VideoWindow(mediaPlayerModel: MediaPlayerModel) {

  def build(): Stage = {

    val mediaViewProperty = new ObjectProperty[javafx.scene.Node]()

    val noVideo = new StringProperty(this, "no-video-string", "No video")

    val noVideoLabel = new Label {
      prefWidth = 1920 / 2
      prefHeight = 1080 / 2
      text <== noVideo
      id = "no-video"
    }

    mediaViewProperty.value = noVideoLabel

    val videoWindow = new Stage {
      title = "Video"
      scene = new Scene {
        stylesheets += classOf[Resource].getResource("/video.css").toString
        root = new BorderPane {
          id = "videoPane"
          center <== mediaViewProperty
        }
      }
    }

    mediaPlayerModel.mediaPlayer.onInvalidate {
      Platform.runLater {
        mediaViewProperty.value = new MediaView(mediaPlayerModel.mediaPlayer()) {
          fitWidth <== videoWindow.scene().widthProperty
          fitHeight <== videoWindow.scene().heightProperty
        }.delegate
      }
    }
    videoWindow
  }
}
