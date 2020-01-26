package muzarto.ui

import scalafx.scene.Node
import scalafx.scene.control.Label
import scalafx.scene.control.Separator
import scalafx.scene.layout.ColumnConstraints
import scalafx.scene.layout.GridPane
import scalafx.scene.layout.Priority

object GroupTitle {

  def build(titleText: String): Node = {

    val title = new Label {
      text = titleText
      styleClass += "groupTitle"
    }

    val left = new Separator() {
      prefWidth = 10
      prefHeight = 2
    }

    val right = new Separator() {
      prefHeight = 2
    }

    val p = new GridPane {
      hgap = 10
      columnConstraints = List(
        new ColumnConstraints(),
        new ColumnConstraints(),
        new ColumnConstraints {
          hgrow = Priority.Always
        }
      )
    }
    p.add(left, 0, 0)
    p.add(title, 1, 0)
    p.add(right, 2, 0)
    p
  }
}
