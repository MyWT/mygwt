package rnd.mywt.client.data;

public interface RowMetaData {

	String getModuleName();

	String getApplicationBeanName();

	String getViewName();

	int getIdColumnIndex();

	int getDisplayColumnIndex();

	int getColumnCount();

	ColumnMetaData[] getColumnMetaDatas();

}