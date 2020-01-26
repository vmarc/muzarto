package muzarto.ui

import muzarto.domain.Cue

import scalafx.Includes._
import scalafx.beans.property.ObjectProperty
import scalafx.collections.ObservableBuffer
import scalafx.scene.Node
import scalafx.scene.control.TableColumn._
import scalafx.scene.control.TableView
import scalafx.scene.input.{KeyCode, KeyEvent}

class CueTable(cues: ObservableBuffer[Cue], selectedCue: ObjectProperty[Option[Cue]]) {

  def build: Node = {
    val table = new TableView[Cue](cues) {
      columns ++= List(
        TableColumnBuilder.build("Cue", 80, (clip: Cue) => clip.number),
        TableColumnBuilder.build("Time", 50, (clip: Cue) => if (clip.manual) "--:--" else Position.format(clip.position)),
        TableColumnBuilder.build("Comment", 500, (clip: Cue) => clip.comment)
      )

      onKeyPressed = (k: KeyEvent) => k.code match {
        case KeyCode.Space =>
          println("space bar pressed from CueTable")
        case _ =>
      }
    }
    TableSelectionBinder.bind(table, selectedCue)
    table
  }
}
