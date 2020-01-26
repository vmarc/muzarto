package muzarto.ui

import muzarto.engine.ShowEventProcessor
import muzarto.model.ShowControlModel

import scalafx.scene.Node
import scalafx.scene.control.SplitPane
import scalafx.scene.layout.BorderPane
import scalafx.scene.layout.Pane

class MainView(model: ShowControlModel, eventProcessor: ShowEventProcessor) {

  def build: Pane = {
    new BorderPane {
      top = showControlView
      center = new SplitPane {
        items.addAll(
          clipTable,
          cueTable
        )
      }
    }
  }

  private def showControlView: Node = {
    new ShowControlView(model, eventProcessor).build
  }

  private def clipTable: Node = {
    new ClipTable(model.clips, model.selectedClip).build
  }

  private def cueTable: Node = {
    new CueTable(model.cues, model.selectedCue).build
  }

}
