package rnd.mywt.client.data.impl;

import java.util.Collection;
import java.util.List;

import rnd.bean.impl.DynaBean;
import rnd.mywt.client.data.DataTable;
import rnd.mywt.client.data.Row;
import rnd.mywt.client.data.RowMetaData;

public class DataTableImpl extends DynaBean implements DataTable {

	public DataTableImpl() {
	}

	public DataTableImpl(RowMetaData rowMetaData) {
		setValue(ROW_META_DATA, rowMetaData);
	}

	public boolean addRow(Row row) {
		return addElement(ROW, row);
	}

	public Row getRow(int index) {
		return (Row) getListValue(ROW).get(index);
	}

	public void addRows(Collection<Row> rows) {
		addAllElement(ROW, rows);
	}

	public List<Row> getRows() {
		return getListValueReadOnly(ROW);
	}

	public int getRowCount() {
		return getListValue(ROW).size();
	}

	public RowMetaData getRowMetaData() {
		return (RowMetaData) getValue(ROW_META_DATA);
	}

	public void addRow(int index, Row row) {
		addElement(ROW, index, row);
	}

	public Row removeRow(int index) {
		return (Row) removeElement(ROW, index);
	}

	public boolean removeRow(Row row) {
		return removeElement(ROW, row);
	}

}
