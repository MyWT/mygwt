package rnd.mywt.client.mvc.field;

import rnd.bean._BoundList;
import rnd.mywt.client.data.ColumnMetaData;
import rnd.mywt.client.data.DataTable;
import rnd.mywt.client.data._Row;

public interface Table extends Field {

	int BEAN_BASED = 0;

	int ROW_BASED = 1;

	// Table Column Meta Data
	String COLUMN_META_DATA = "columnMetaData";

	void setColumnMetaDatas(ColumnMetaData[] columnMetaData);

	ColumnMetaData[] getColumnMetaDatas();

	// Table Model
	public interface TableModel extends FieldModel {

		boolean isUpdatable();

		void initializeListner();

	}

	// Row Table Model
	public interface RowTableModel extends TableModel {

		// Data Table
		String DATA_TABLE = "dataTable";

		void setDataTable(DataTable dataTable);

		DataTable getDataTable();

		String CURRENT_ROW = "currentRow";

		_Row getCurrentRow();

		void addRow(_Row newRow);

		void addRow(int index, _Row newRow);

		void removeRow(_Row row);

		void removeCurrentRow();

		void updateRow(_Row oldRow, _Row newRow);

		void updateCurrentRow(_Row newRow);
	}

	// _Bean Table Model
	public interface BeanTableModel extends TableModel {

		public interface RowDelegate {
			Object newRow();
		}

		// Row Delegate
		String ROW_DELEGATE = "rowDelegate";

		void setRowDelegate(RowDelegate rowDelegate);

		RowDelegate getRowDelegate();

		// Data List
		String DATA_LIST = "dataList";

		void setDataList(_BoundList dataList);

		_BoundList getDataList();

		public void insertRow();

		void removeRow(int index);
	}

	// Table View
	public interface TableView extends FieldView {

		void refresh();

		void initialiseListner();

	}

	public interface BeanTableView extends TableView {

	}

}