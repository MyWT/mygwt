package rnd.mywt.client.data;

import java.util.Collection;
import java.util.List;

public interface DataTable {

	String ROW_COUNT = "rowCount";

	int getRowCount();

	String ROW = "row";

	void addRows(Collection<_Row> rows);

	boolean addRow(_Row row);

	void addRow(int index, _Row row);

	boolean removeRow(_Row row);

	_Row removeRow(int index);

	_Row getRow(int index);

	List<_Row> getRows();

	String ROW_META_DATA = "rowMetaData";

	RowMetaData getRowMetaData();

}