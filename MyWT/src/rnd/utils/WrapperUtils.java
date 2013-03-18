package rnd.utils;

/**
 * @description This class is for Wrapper-Primtive data type conversion with null checking
 * 
 */

public class WrapperUtils {

	private WrapperUtils() {
	}

	// Double
	public static Double getDouble(Object doubleValue) {
		if (doubleValue == null) {
			return new Double(0.0);
		}
		if (doubleValue instanceof String) {
			return new Double((String) doubleValue);
		}
		return new Double(((Number) doubleValue).doubleValue());
	}

	// Long
	public static Long getLong(Object longValue) {
		if (longValue == null) {
			return new Long(0);
		}
		if (longValue instanceof String) {
			return new Long((String) longValue);
		}
		return new Long(((Number) longValue).longValue());
	}

	// Integer
	public static int getInteger(Object intValue) {
		if (intValue == null) {
			return new Integer(0);
		}
		if (intValue instanceof String) {
			return new Integer((String) intValue);
		}
		return new Integer(((Number) intValue).intValue());
	}

	// Short
	public static Short getShort(Object shortValue) {
		if (shortValue == null) {
			return new Short((short) 0);
		}
		if (shortValue instanceof String) {
			return new Short((String) shortValue);
		}
		return new Short(((Number) shortValue).shortValue());
	}

	// Boolean
	public static Boolean getBoolean(Object booleanValue) {
		if (booleanValue == null) {
			return Boolean.FALSE;
		}
		if (booleanValue instanceof String) {
			return new Boolean((String) booleanValue);
		}
		return (Boolean) booleanValue;
	}
}
