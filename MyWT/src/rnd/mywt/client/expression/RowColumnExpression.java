package rnd.mywt.client.expression;

import rnd.expression.AbstractExpression;
import rnd.mywt.client.data.ColumnMetaData;
import rnd.mywt.client.data.Row;

public class RowColumnExpression extends AbstractExpression {

	private String columnName;

	private int index = -1;

	private boolean indexCalculated;

	public RowColumnExpression() {
	}

	public RowColumnExpression(String columnName) {
		this.columnName = columnName;
	}

	@Override
	public Object getValue(Object object) {
		Row row = (Row) object;
		return row.getColumns().get(getColumnIndex(row));
	}

	private int getColumnIndex(Row row) {

		if (!this.indexCalculated) {
			ColumnMetaData[] columnMetaDatas = row.getRowMetaData().getColumnMetaDatas();

			for (int i = 0; i < columnMetaDatas.length; i++) {
				if (columnMetaDatas[i].getName().equals(this.columnName)) {
					this.indexCalculated = true;
					this.index = i;
					break;
				}
			}
			// throw new IllegalArgumentException("Row doesn't containt required column");
		}

		return this.index;
	}
}