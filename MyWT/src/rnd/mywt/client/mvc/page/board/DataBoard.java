package rnd.mywt.client.mvc.page.board;

import rnd.mywt.client.data.FilterInfo;
import rnd.mywt.client.data.Row;
import rnd.mywt.client.mvc.field.Table;
import rnd.mywt.client.mvc.field.data.ReferenceField;

public interface DataBoard extends Board {

	String VIEW_NAME = "viewName";

	String getViewName();

	// Table Operation

	Table getTable();

	void refreshTable();

	// Row Operation

	void addRow(Row newRow);

	void removeCurrentRow();

	void updateCurrentRow(Row updatedRow);

	// Referenced Data Board

	ReferenceField getReferenceField();

	void setReferenceField(ReferenceField referenceField);

	// Filter Operation

	String FILTER = "filter";

	FilterInfo getFilter();

	void setFilter(FilterInfo filterInfo);

	// Model

	public interface DataBoardModel extends Model {

		String DATA_TABLE_INTIALIZED = "dataTableIntialized";

		boolean isDataTableIntialized();

		void setDataTableIntialized(boolean dataTableIntialized);

		String DATA_TABLE_META_DATA_INTIALIZED = "dataTableMetaDataIntialized";

		boolean isDataTableMetaDataIntialized();

		void setDataTableMetaDataIntialized(boolean dataTableMetaDataIntialized);

		String FILTER_RESET = "filterReset";

		public boolean isFilterReset();

		public void setFilterReset(boolean filterReset);

	}

	// View
	
	public interface DataBoardView extends View {
	}

}
