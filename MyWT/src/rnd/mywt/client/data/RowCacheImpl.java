package rnd.mywt.client.data;

import java.util.Collection;

import rnd.java.util.NestedHashMap;

public class RowCacheImpl implements RowCache {

	private NestedHashMap rowCacheMap;

	private RowCacheImpl() {
		rowCacheMap = new NestedHashMap(2);
	}

	private static class RowCacheHolder {
		private static RowCache rowCache = new RowCacheImpl();
	}

	public synchronized static RowCache get() {
		return RowCacheHolder.rowCache;
	}

	public Object addRow(String moduleName, String applicationBeanName, String viewName, Row row) {
		return rowCacheMap.put(wrapKey(moduleName, applicationBeanName, viewName, row.getId()), row);
	}

	public void flushCache() {
		rowCacheMap.clear();
	}

	public Row getRow(String moduleName, String applicationBeanName, String viewName, Long rowId) {
		return (Row) rowCacheMap.get(wrapKey(moduleName, applicationBeanName, viewName, rowId));
	}

	public Row removeRow(String moduleName, String applicationBeanName, String viewName, Long rowId) {
		return (Row) rowCacheMap.remove(wrapKey(moduleName, applicationBeanName, viewName, rowId));
	}

	public Collection<Row> getRows(String moduleName, String applicationBeanName, String viewName) {
		return (Collection<Row>) rowCacheMap.get(wrapKey(moduleName, applicationBeanName, viewName, null));
	}

	public void removeRows(String moduleName, String applicationBeanName, String viewName) {
		rowCacheMap.remove(wrapKey(moduleName, applicationBeanName, viewName, null));
	}

	private Object[] wrapKey(String moduleName, String applicationBeanName, String viewName, Long rowId) {
		return new Object[] { moduleName + "." + applicationBeanName + "." + viewName, rowId };
		// return new Object[] { moduleName, applicationBeanName, viewName, rowId };
	}

}
