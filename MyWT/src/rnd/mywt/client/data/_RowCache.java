package rnd.mywt.client.data;

import java.util.Collection;

public interface _RowCache {

	Object addRow(String moduleName, String applicationBeanName, String viewName, _Row row);

	Object removeRow(String moduleName, String applicationBeanName, String viewName, Long rowId);

	void removeRows(String moduleName, String applicationBeanName, String viewName);

	_Row getRow(String moduleName, String applicationBeanName, String viewName, Long rowId);

	Collection<_Row> getRows(String moduleName, String applicationBeanName, String viewName);

	void flushCache();

}
