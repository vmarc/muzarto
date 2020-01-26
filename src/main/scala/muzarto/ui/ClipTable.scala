package muzarto.ui

import muzarto.domain.Clip

import scalafx.Includes._
import scalafx.beans.property.ObjectProperty
import scalafx.collections.ObservableBuffer
import scalafx.scene.Node
import scalafx.scene.control.TableColumn._
import scalafx.scene.control.TableView
import scalafx.scene.input.{KeyCode, KeyEvent}

class ClipTable(clips: ObservableBuffer[Clip], selectedClip: ObjectProperty[Option[Clip]]) {

  def build: Node = {
    val table = new TableView[Clip](clips) {
      columns ++= List(
        TableColumnBuilder.build("Clip", 80, (clip: Clip) => clip.number),
        TableColumnBuilder.build("Time", 50, (clip: Clip) => Position.format(clip.contentDuration)),
        TableColumnBuilder.build("Comment", 500, (clip: Clip) => clip.comment)
      )

      onKeyPressed = (k: KeyEvent) => k.code match {
        case KeyCode.Space => println("space bar pressed from ClipTable")
        case _ =>
      }
    }
    TableSelectionBinder.bind(table, selectedClip)

    table
  }
}
