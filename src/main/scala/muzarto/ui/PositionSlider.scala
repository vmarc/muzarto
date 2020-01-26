package muzarto.ui

import muzarto.engine.ShowEventProcessor
import muzarto.domain.Clip
import muzarto.model.MediaPlayerModel

import scalafx.application.Platform
import scalafx.beans.property.ObjectProperty
import scalafx.scene.control.Slider
import scalafx.scene.media.MediaPlayer.Status

class PositionSlider(
  selectedClip: ObjectProperty[Option[Clip]],
  mediaPlayerModel: MediaPlayerModel,
  eventProcessor: ShowEventProcessor
) {

  def build: Slider = {

    val slider = new Slider {
      min = 0
      id = "positionSlider"
      max = 1
      value = 0
    }

    mediaPlayerModel.currentTime.onInvalidate {
      Platform.runLater {
        if (!slider.isValueChanging) {
          val currentTime = mediaPlayerModel.currentTime.value
          slider.value = selectedClip.value match {
            case None => 0d
            case Some(clip) =>
              if (clip.contentDuration > 0) {
                currentTime / clip.contentDuration
              }
              else {
                0d
              }
          }
        }
      }
    }

    mediaPlayerModel.status.onInvalidate {
      Platform.runLater {
        val status = mediaPlayerModel.status.value
        slider.disable = Status.Unknown == status || status == null
      }
    }

    slider.value.onInvalidate {
      if (slider.valueChanging.value) {
        eventProcessor.userUpdatingPosition(slider.value.value)
      }
    }

    slider
  }
}
