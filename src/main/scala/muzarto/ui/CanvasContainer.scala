package muzarto.ui

import scalafx.scene.canvas.Canvas

class CanvasContainer(canvas: Canvas) extends javafx.scene.layout.Pane {

  getChildren.add(canvas)

  protected override def layoutChildren(): Unit = {
    super.layoutChildren()
    val x = snappedLeftInset
    val y = snappedTopInset
    val w = snapSize(getWidth) - x - snappedRightInset
    val h = snapSize(getHeight) - y - snappedBottomInset
    canvas.setLayoutX(x)
    canvas.setLayoutY(y)
    canvas.setWidth(w)
    canvas.setHeight(h)
  }
}
