package rnd.mywt.client.data;

import java.util.Collection;
import java.util.List;

public interface DataTable {

	String ROW_COUNT = "rowCount";

	int getRowCount();

	String ROW = "row";

	void addRows(Collection<Row> rows);

	boolean addRow(Row row);

	void addRow(int index, Row row);

	boolean removeRow(Row row);

	Row removeRow(int index);

	Row getRow(int index);

	List<Row> getRows();

	String ROW_META_DATA = "rowMetaData";

	RowMetaData getRowMetaData();

}