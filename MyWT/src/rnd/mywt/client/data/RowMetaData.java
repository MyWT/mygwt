package rnd.mywt.client.data;

public interface RowMetaData {

	int getIdColumnIndex();

	int getDisplayColumnIndex();

	int getColumnCount();

	ColumnMetaData[] getColumnMetaDatas();

}