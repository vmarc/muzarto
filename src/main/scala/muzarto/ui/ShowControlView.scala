package muzarto.ui

import muzarto.engine.ShowEventProcessor
import muzarto.model.ShowControlModel

import scala.language.postfixOps
import scalafx.geometry.Insets
import scalafx.geometry.Pos
import scalafx.scene.Node
import scalafx.scene.control.Label
import scalafx.scene.layout.ColumnConstraints
import scalafx.scene.layout.GridPane
import scalafx.scene.layout.Priority

class ShowControlView(model: ShowControlModel, eventProcessor: ShowEventProcessor) {

  def build: Node = {

    val noConstraints = new ColumnConstraints()
    val timeConstraints = new ColumnConstraints {
      prefWidth = 140
    }
    val growing = new ColumnConstraints {
      fillWidth = true
      hgrow = Priority.Always
    }

    val gridPane = new GridPane {
      hgap = 5
      vgap = 5
      padding = Insets(10)
      columnConstraints = List(
        noConstraints,
        noConstraints,
        timeConstraints,
        growing,
        noConstraints
      )
    }

    gridPane.add(clipGroupTitle, 0, 0, 5, 1)

    gridPane.add(playPauseButton, 0, 1)
    //gridPane.add(stopButton, 1, 1)
    gridPane.add(currentTimeLabel, 2, 1)
    gridPane.add(clipTextLabel, 3, 1)
    gridPane.add(statusLabel, 4, 1)

    gridPane.add(positionSlider, 2, 2, 3, 1)

    gridPane.add(timeLine, 2, 3, 3, 1)

    gridPane.add(cueGroupTitle, 0, 4, 5, 1)

    gridPane.add(cueButton, 0, 5)
    gridPane.add(timeToCueLabel, 2, 5)
    gridPane.add(cueTextLabel, 3, 5, 2, 1)

    gridPane
  }

  private def clipGroupTitle: Node = {
    GroupTitle.build("Sound clip")
  }

  private def playPauseButton: Node = {
    new PlayPauseButton(
      model.selectedClip,
      model.mediaPlayerModel.status,
      eventProcessor
    ).build
  }

  private def stopButton: Node = {
    new StopButton(
      model.selectedClip,
      eventProcessor
    ).build
  }

  private def currentTimeLabel: Node = {
    new Label {
      text <== model.clipTimeString
      styleClass += "currentTime"
    }
  }

  private def clipTextLabel: Node = {
    new Label {
      text <== model.clipText
      styleClass += "clipText"
    }
  }

  private def statusLabel: Node = {
    new Label {
      text <== model.statusString
      alignmentInParent = Pos.TopRight
      styleClass += "statusDisplay"
    }
  }

  private def positionSlider: Node = {
    new PositionSlider(
      model.selectedClip,
      model.mediaPlayerModel,
      eventProcessor
    ).build
  }

  private def timeLine: Node = {
    new TimeLine(
      model.selectedClip,
      model.selectedCue
    ).build
  }

  private def cueGroupTitle: Node = {
    GroupTitle.build("Light cue")
  }

  private def cueButton: Node = {
    new CueButton(
      model.selectedCue,
      eventProcessor
    ).build
  }

  private def timeToCueLabel: Node = {
    new Label {
      text <== model.timeToCue
      styleClass += "timeToCue"
    }
  }

  private def cueTextLabel: Node = {
    new Label {
      text <== model.cueText
      styleClass += "cueText"
    }
  }

}
