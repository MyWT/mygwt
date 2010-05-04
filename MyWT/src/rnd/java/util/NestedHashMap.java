package rnd.java.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class NestedHashMap implements Map {

	private class ChildHashMap extends HashMap {

		private Map parentMap;

		// private int childDepth;

		private ChildHashMap(Map parentMap, int depth) {
			this.parentMap = parentMap;
			// this.childDepth = depth;
		}

		public Map getParentMap() {
			return parentMap;
		}

		// public int getDepth() {
		// return childDepth;
		// }

	}

	private HashMap<?, ChildHashMap> rootMap;

	private int depth;

	public NestedHashMap(int depth) {
		this.depth = depth;
	}

	public int getDepth() {
		return depth;
	}

	public Object remove(Object key) {

		if (isEmpty()) {
			return null;
		}

		Object[] keys = validateKey(key);

		Map childMap = null;
		Map parentMap = rootMap;

		int i = 0;
		int j = keys.length - 1;
		while (i < j) {
			childMap = (Map) parentMap.get(keys[i++]);
			if (childMap == null) {
				break;
			}
			parentMap = childMap;
		}

		if (childMap != null) {
			Object oldValue = null;
			if (keys[i] == null) {
				childMap.clear();
			} else {
				oldValue = childMap.remove(keys[i]);
			}
			if (childMap.isEmpty()) {
				i = j;
				while (i > 1) {
					parentMap.remove(keys[i--]);
					if (parentMap.isEmpty()) {
						parentMap = ((ChildHashMap) parentMap).getParentMap();
					} else {
						break;
					}
				}
				if (i == 1) {
					rootMap.clear();
					rootMap = null;
				}
			}
			return oldValue;
		}
		return null;
	}

	public void clear() {
		if (isEmpty()) {
			return;
		}

		Iterator iter = rootMap.values().iterator();

		while (iter.hasNext()) {
			Map childHashMap = (Map) iter.next();
			childHashMap.clear();
		}
		rootMap.clear();
		rootMap = null;
	}

	public boolean containsKey(Object key) {
		return get(key) != null;
	}

	public boolean containsValue(Object value) {
		return values().contains(value);
	}

	public Set entrySet() {
		throw new UnsupportedOperationException();
	}

	public Object get(Object key) {

		if (isEmpty()) {
			return null;
		}

		Object[] keys = validateKey(key);

		ChildHashMap childMap = null;
		HashMap<?, ChildHashMap> parentMap = rootMap;

		int i = 0;
		int j = keys.length - 1;
		while (i < j) {
			childMap = parentMap.get(keys[i++]);
			if (childMap == null) {
				break;
			}
			parentMap = childMap;
		}

		if (i == j && childMap != null) {
			if (keys[j] == null) {
				return childMap.values();
			}
			return childMap.get(keys[j]);
		}
		return null;
	}

	public boolean isEmpty() {
		return rootMap == null;
	}

	public Set keySet() {
		throw new UnsupportedOperationException();
	}

	public Object put(Object key, Object value) {

		Object[] keys = validateKey(key);

		initRootMap();

		Map childMap = null;
		Map parentMap = rootMap;

		Object childKey = null;

		int i = 0;
		int j = keys.length - 1;
		while (i < j) {
			childKey = keys[i++];
			childMap = (Map) parentMap.get(childKey);
			if (childMap == null) {
				childMap = new ChildHashMap(parentMap, depth - i);
				parentMap.put(childKey, childMap);
			}
			parentMap = childMap;
		}

		return childMap.put(keys[j], value);
	}

	private Object[] validateKey(Object key) {
		if (!key.getClass().isArray()) {
			throw new IllegalArgumentException("Key not supported");
		}
		Object[] keys = (Object[]) key;
		if (keys.length != this.depth) {
			throw new IllegalArgumentException("Invalid key depth");
		}
		return keys;
	}

	private void initRootMap() {
		if (rootMap == null) {
			rootMap = new HashMap();
		}
	}

	public void putAll(Map t) {
		throw new UnsupportedOperationException();
	}

	public int size() {
		throw new UnsupportedOperationException();
	}

	public Collection values() {
		throw new UnsupportedOperationException();
	}

	static int tempValue = 0;

	// public static void main(String[] args) throws IOException {
	//
	// // NestedHashMap map = new NestedHashMap(4);
	// //
	// // Object[] key = new Object[] { 1.0, 2.0, 3.0, 4.0 };
	// // put(map, key);
	// //
	// // key = new Object[] { 1.0, 2.0, 3.0, 4.1 };
	// // put(map, key);
	// //
	// // key = new Object[] { 1.0, 2.0, 3.1, 4.1 };
	// // put(map, key);
	// //
	// // key = new Object[] { 1.0, 2.1, 3.1, 4.1 };
	// // put(map, key);
	// //
	// // key = new Object[] { 1.1, 2.1, 3.1, 4.1 };
	// // put(map, key);
	//
	// NestedHashMap map = new NestedHashMap(2);
	// Object[] key = null;
	//
	// // key = new Object[] { 1.0, 2.0 };
	// // put(map, key);
	// //
	// // key = new Object[] { 1.0, 2.1 };
	// // put(map, key);
	// //
	// // key = new Object[] { 1.0, 2.2 };
	// // put(map, key);
	// //
	// // key = new Object[] { 1.1, 2.0 };
	// // put(map, key);
	// //
	// // key = new Object[] { 1.1, 2.1 };
	// // put(map, key);
	// //
	// // key = new Object[] { 1.1, 2.2 };
	// // put(map, key);
	// //
	// // key = new Object[] { 1.2, 2.0 };
	// // put(map, key);
	// //
	// // key = new Object[] { 1.2, 2.1 };
	// // put(map, key);
	// //
	// // key = new Object[] { 1.2, 2.2 };
	// // put(map, key);
	//
	// key = new Object[] { 0.0, 1.0 };
	// put(map, key);
	// System.out.println("map_get:" + map.get(key));
	//
	// key = new Object[] { 0.0, 1.1 };
	// put(map, key);
	// System.out.println("map_get:" + map.get(key));
	//
	// key = new Object[] { 0.1, 1.0 };
	// put(map, key);
	// System.out.println("map_get:" + map.get(key));
	//
	// key = new Object[] { 0.1, 1.1 };
	// put(map, key);
	// System.out.println("map_get:" + map.get(key));
	//
	// System.in.read();
	//
	// }

	// private static void put(NestedHashMap map, Object[] key) {
	// map.put(key, tempValue++);
	// }

}
