package muzarto.ui

import scalafx.beans.property.ReadOnlyObjectWrapper
import scalafx.scene.control.TableColumn

object TableColumnBuilder {

  def build[T](header: String, columnWidth: Double, accessor: (T) => String): TableColumn[T, String] = {
    new TableColumn[T, String] {
      text = header
      cellValueFactory = { r => ReadOnlyObjectWrapper(accessor(r.value)) }
      prefWidth = columnWidth
    }
  }
}
