package muzarto.ui

import muzarto.engine.ShowEventProcessor
import muzarto.domain.Cue

import scalafx.Includes._
import scalafx.beans.property.ObjectProperty
import scalafx.event.ActionEvent
import scalafx.scene.Node
import scalafx.scene.control.Button

class CueButton(
  selectedCue: ObjectProperty[Option[Cue]],
  eventProcessor: ShowEventProcessor
) {

  def build: Node = {
    val button = new Button {
      id = "cueButton"
      onAction = { (ae: ActionEvent) =>
        selectedCue.value match {
          case Some(cue) => eventProcessor.userTriggerCue(cue)
          case None =>
        }
      }
    }
    button.disable = selectedCue.value.isEmpty
    selectedCue.onInvalidate {
      button.disable = selectedCue.value.isEmpty
    }
    button
  }
}
