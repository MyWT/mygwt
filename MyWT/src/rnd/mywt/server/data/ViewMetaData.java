package rnd.mywt.server.data;

public interface ViewMetaData {

	String getViewName();

	String[] getViewColumnsNames();

	String[] getViewColumnsExpressions();

	int getIdColumnIndex();

	String getIdColumnName();

	int getDisplayColumnIndex();

	String getDisplayColumnName();

	String getFilterExpression(String filterName);

}
