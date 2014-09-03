package rnd.mywt.client.data.impl;

import java.util.Collection;
import java.util.List;

import rnd.bean.impl.DynaBean;
import rnd.mywt.client.data.DataTable;
import rnd.mywt.client.data._Row;
import rnd.mywt.client.data.RowMetaData;

public class DataTableImpl extends DynaBean implements DataTable {

	public DataTableImpl() {
	}

	public DataTableImpl(RowMetaData rowMetaData) {
		setValue(ROW_META_DATA, rowMetaData);
	}

	public boolean addRow(_Row row) {
		return addElement(ROW, row);
	}

	public _Row getRow(int index) {
		return (_Row) getListValue(ROW).get(index);
	}

	public void addRows(Collection<_Row> rows) {
		addAllElement(ROW, rows);
	}

	public List<_Row> getRows() {
		return getListValueReadOnly(ROW);
	}

	public int getRowCount() {
		return getListValue(ROW).size();
	}

	public RowMetaData getRowMetaData() {
		return (RowMetaData) getValue(ROW_META_DATA);
	}

	public void addRow(int index, _Row row) {
		addElement(ROW, index, row);
	}

	public _Row removeRow(int index) {
		return (_Row) removeElement(ROW, index);
	}

	public boolean removeRow(_Row row) {
		return removeElement(ROW, row);
	}

}
