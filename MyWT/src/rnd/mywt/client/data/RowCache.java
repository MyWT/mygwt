package rnd.mywt.client.data;

import java.util.Collection;

public interface RowCache {

	Object addRow(String moduleName, String applicationBeanName, String viewName, Row row);

	Object removeRow(String moduleName, String applicationBeanName, String viewName, Long rowId);

	void removeRows(String moduleName, String applicationBeanName, String viewName);

	Row getRow(String moduleName, String applicationBeanName, String viewName, Long rowId);

	Collection<Row> getRows(String moduleName, String applicationBeanName, String viewName);

	void flushCache();

}
