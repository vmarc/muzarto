package muzarto.ui

import muzarto.engine.ShowEventProcessor
import muzarto.domain.Clip

import scalafx.Includes._
import scalafx.beans.property.ObjectProperty
import scalafx.beans.property.ReadOnlyObjectProperty
import scalafx.event.ActionEvent
import scalafx.scene.Node
import scalafx.scene.control.Button
import scalafx.scene.image.ImageView
import scalafx.scene.media.MediaPlayer
import scalafx.scene.media.MediaPlayer.Status

class PlayPauseButton(
  selectedClip: ObjectProperty[Option[Clip]],
  status: ReadOnlyObjectProperty[MediaPlayer.Status],
  eventProcessor: ShowEventProcessor
) {

  def build: Node = {
    val button = new Button {
      graphic = buildPauseIcon()
      id = "playPauseButton"
      onAction = (ae: ActionEvent) => {
        status.value match {
          case Status.Playing => eventProcessor.userPause()
          case _ => eventProcessor.userPlay()
        }
      }
    }

    button.disable = selectedClip.value.isEmpty
    selectedClip.onInvalidate {
      button.disable = selectedClip.value.isEmpty
    }

    button
  }

  private def buildPauseIcon(): ImageView = {
    val pauseImg = Resource.image("pause.png")
    val playImg = Resource.image("play.png")
    val playPauseIcon: ImageView = new ImageView {
      image = playImg
    }
    status.onInvalidate {
      playPauseIcon.image = status.value match {
        case Status.Playing => pauseImg
        case _ => playImg
      }
    }
    playPauseIcon
  }
}
