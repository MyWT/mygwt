package rnd.mywt.client.data;

import java.util.List;

public interface _Row {

	Long getId();

	String getDisplayName();

	String COLUMN = "column";

	void setColumns(List columns);

	List getColumns();

	String ROW_META_DATA = "rowMetaData";

	RowMetaData getRowMetaData();

}