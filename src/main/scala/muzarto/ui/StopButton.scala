package muzarto.ui

import muzarto.engine.ShowEventProcessor
import muzarto.domain.Clip

import scalafx.Includes._
import scalafx.beans.property.ObjectProperty
import scalafx.event.ActionEvent
import scalafx.scene.Node
import scalafx.scene.control.Button

class StopButton(
  selectedClip: ObjectProperty[Option[Clip]],
  eventProcessor: ShowEventProcessor
) {

  def build: Node = {
    val button = new Button {
      id = "stopButton"
      onAction = (ae: ActionEvent) => eventProcessor.userStop()
    }
    button.disable = selectedClip.value.isEmpty
    selectedClip.onInvalidate {
      button.disable = selectedClip.value.isEmpty
    }
    button
  }
}
