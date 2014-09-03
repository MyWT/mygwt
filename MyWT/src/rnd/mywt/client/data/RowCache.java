package rnd.mywt.client.data;

import java.util.Collection;

import rnd.java.util.NestedHashMap;

public class RowCache implements _RowCache {

	private NestedHashMap rowCacheMap;

	private RowCache() {
		rowCacheMap = new NestedHashMap(2);
	}

	private static class RowCacheHolder {
		private static _RowCache rowCache = new RowCache();
	}

	public synchronized static _RowCache get() {
		return RowCacheHolder.rowCache;
	}

	public Object addRow(String moduleName, String applicationBeanName, String viewName, _Row row) {
		return rowCacheMap.put(wrapKey(moduleName, applicationBeanName, viewName, row.getId()), row);
	}

	public void flushCache() {
		rowCacheMap.clear();
	}

	public _Row getRow(String moduleName, String applicationBeanName, String viewName, Long rowId) {
		return (_Row) rowCacheMap.get(wrapKey(moduleName, applicationBeanName, viewName, rowId));
	}

	public _Row removeRow(String moduleName, String applicationBeanName, String viewName, Long rowId) {
		return (_Row) rowCacheMap.remove(wrapKey(moduleName, applicationBeanName, viewName, rowId));
	}

	public Collection<_Row> getRows(String moduleName, String applicationBeanName, String viewName) {
		return (Collection<_Row>) rowCacheMap.get(wrapKey(moduleName, applicationBeanName, viewName, null));
	}

	public void removeRows(String moduleName, String applicationBeanName, String viewName) {
		rowCacheMap.remove(wrapKey(moduleName, applicationBeanName, viewName, null));
	}

	private Object[] wrapKey(String moduleName, String applicationBeanName, String viewName, Long rowId) {
		return new Object[] { moduleName + "." + applicationBeanName + "." + viewName, rowId };
		// return new Object[] { moduleName, applicationBeanName, viewName, rowId };
	}

}
