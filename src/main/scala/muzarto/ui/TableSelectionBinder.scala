package muzarto.ui

import scalafx.Includes._
import scalafx.beans.property.ObjectProperty
import scalafx.scene.control.SelectionMode
import scalafx.scene.control.TableView

object TableSelectionBinder {

  def bind[T](table: TableView[T], selectedItem: ObjectProperty[Option[T]]): Unit = {

    var selectionUpdateFromModel = false
    var selectionUpdateFromComponent = false

    table.selectionModel().selectionMode = SelectionMode.Single

    selectedItem.value match {
      case Some(clip) => table.selectionModel().select(clip)
      case None => table.selectionModel().clearSelection()
    }

    selectedItem.onChange(
      (_, oldItem, newItem) => {
        if (!selectionUpdateFromComponent){
          selectionUpdateFromModel = true
          try {
            selectedItem.value match {
              case Some(clip) => table.selectionModel().select(clip)
              case None => table.selectionModel().clearSelection()
            }
          }
          finally {
            selectionUpdateFromModel = false
          }
        }
      }
    )

    table.selectionModel().selectedItem.onChange(
      (_, oldItem, newItem) => {
        if (!selectionUpdateFromModel) {
          selectionUpdateFromComponent = true
          try {
            selectedItem.value = Option(newItem)
          }
          finally {
            selectionUpdateFromComponent = false
          }
        }
      }
    )
  }
}
