package rnd.mywt.client.utils;

import java.util.Collection;
import java.util.Iterator;

public class ObjectUtils {

	public static boolean areEqual(Object firstValue, Object secondValue) {
		return (firstValue == secondValue) || (firstValue == null && secondValue == null) || (firstValue != null && secondValue != null && firstValue.equals(secondValue));
	}

	public static String toString(Object obj, String seperator) {
		if (obj == null) { return ""; }
		if (obj instanceof Collection) {
			Collection coll = (Collection) obj;
			StringBuffer sb = new StringBuffer();
			if (coll != null) {
				Iterator iter = coll.iterator();
				if (iter.hasNext()) {
					sb.append(iter.next());
				}
				while (iter.hasNext()) {
					sb.append(seperator).append(iter.next());
				}
			}
			return sb.toString();
		} else if (obj.getClass().isArray()) {
			Object[] objs = (Object[]) obj;
			StringBuffer sb = new StringBuffer();
			if (objs.length > 0) {
				sb.append(objs[0]);
				for (int i = 1; i < objs.length; i++) {
					sb.append(seperator).append(objs[i]);
				}
			}
			return sb.toString();
		}
		return obj.toString();
	}

}