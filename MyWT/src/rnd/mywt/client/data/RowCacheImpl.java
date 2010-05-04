package rnd.mywt.client.data;

import java.util.Collection;

import rnd.java.util.NestedHashMap;

public class RowCacheImpl implements RowCache {

	private NestedHashMap nestedHashMap;

	private RowCacheImpl() {
		nestedHashMap = new NestedHashMap(2);
	}

	private static class RowCacheHolder {
		private static RowCache rowCache = new RowCacheImpl();
	}

	public synchronized static RowCache get() {
		return RowCacheHolder.rowCache;
	}

	public Object addRow(String moduleName, String applicationBeanName, String viewName, Row row) {
		return nestedHashMap.put(wrapKey(moduleName, applicationBeanName, viewName, row.getId()), row);
	}

	public void flushCache() {
		nestedHashMap.clear();
	}

	public Row getRow(String moduleName, String applicationBeanName, String viewName, Long rowId) {
		return (Row) nestedHashMap.get(wrapKey(moduleName, applicationBeanName, viewName, rowId));
	}

	public Row removeRow(String moduleName, String applicationBeanName, String viewName, Long rowId) {
		return (Row) nestedHashMap.remove(wrapKey(moduleName, applicationBeanName, viewName, rowId));
	}

	public Collection<Row> getRows(String moduleName, String applicationBeanName, String viewName) {
		return (Collection<Row>) nestedHashMap.get(wrapKey(moduleName, applicationBeanName, viewName, null));
	}

	public void removeRows(String moduleName, String applicationBeanName, String viewName) {
		nestedHashMap.remove(wrapKey(moduleName, applicationBeanName, viewName, null));
	}

	private Object[] wrapKey(String moduleName, String applicationBeanName, String viewName, Long rowId) {
		return new Object[] { moduleName + "." + applicationBeanName + "." + viewName, rowId };
		// return new Object[] { moduleName, applicationBeanName, viewName, rowId };
	}

}
