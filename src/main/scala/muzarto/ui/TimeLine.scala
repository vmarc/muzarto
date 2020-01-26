package muzarto.ui

import muzarto.domain.Clip
import muzarto.domain.Cue

import scalafx.Includes._
import scalafx.beans.property.ObjectProperty
import scalafx.scene.Node
import scalafx.scene.canvas.Canvas
import scalafx.scene.paint.Color

class TimeLine(
  selectedClip: ObjectProperty[Option[Clip]],
  selectedCue: ObjectProperty[Option[Cue]]
) {

  private val canvas = new Canvas(500, 10)

  def build: Node = new CanvasContainer(canvas)

  selectedClip.onInvalidate {
    repaint()
  }

  selectedCue.onInvalidate {
    repaint()
  }

  canvas.width.onInvalidate {
    repaint()
  }

  private def repaint(): Unit = new TimeLinePainter().repaint()

  private class TimeLinePainter() {

    private val sliderThumbRadius = 8
    private val width = canvas.width.value
    private val height = canvas.height.value
    private val xStart = sliderThumbRadius
    private val xEnd = width - sliderThumbRadius
    private val gc = canvas.graphicsContext2D

    def repaint(): Unit = {
      clearCanvas()
      paintStartAndEndTicks()
      paintCuePositions()
    }

    private def clearCanvas(): Unit = {
      gc.clearRect(0, 0, width, height)
    }

    private def paintStartAndEndTicks(): Unit = {
      gc.lineWidth = 1
      gc.stroke = Color.rgb(255, 255, 255)
      paintStartTick()
      paintEndTick()
    }

    private def paintStartTick(): Unit = {
      val shouldPaint = selectedClip.value.flatMap(_.cues.headOption) match {
        case Some(cue) => cue.position > 0d
        case None => false
      }
      if (shouldPaint) {
        gc.strokeLine(xStart, 0, xStart, height)
      }
    }

    private def paintEndTick(): Unit = {
      gc.strokeLine(xEnd, 0, xEnd, height)
    }

    private def paintCuePositions(): Unit = {
      selectedClip.value.foreach { clip =>
        clip.cues.foreach { cue =>
          gc.stroke = cuePositionColor(cue)
          val x = xStart + (cuePosition(clip, cue) * (xEnd - xStart))
          gc.strokeLine(x, 0, x, height)
        }
      }
    }

    private def cuePositionColor(cue: Cue): Color = {
      if (selectedCue.value.contains(cue)) {
        Color.rgb(0, 255, 0)
      }
      else {
        Color.rgb(255, 0, 0)
      }
    }

    private def cuePosition(clip: Clip, cue: Cue): Double = {
      cue.position / clip.contentDuration
    }
  }

}
