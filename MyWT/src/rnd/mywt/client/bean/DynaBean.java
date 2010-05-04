package rnd.mywt.client.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


/**
 * @description DynaBean is just serializable implementation of Bean
 * 
 * @author vinodp
 * 
 */
public class DynaBean extends AbstractBean implements Serializable {

	private HashMap<String, Object> propertyMap = new HashMap(0);

	private HashMap<String, List> indexedPropertyMap = new HashMap(0);

	// Property

	public final Set getPropertyNames() {
		return getPropertyMap().keySet();
	}

	@Override
	protected final Object getValue0(String propertyName) {
		return getPropertyMap().get(propertyName);
	}

	@Override
	protected final Object setValue0(String propertyName, Object newValue) {
		return getPropertyMap().put(propertyName, newValue);
	}

	protected HashMap<String, Object> getPropertyMap() {
		return this.propertyMap;
	}

	// Indexed Property

	public final Set getIndexedPropertyNames() {
		return getIndexedPropertyMap().keySet();
	}

	@Override
	protected final List getListValue0(String propertyName) {
		return getIndexedPropertyMap().get(propertyName);
	}

	@Override
	protected final List setListValue0(String propertyName, List newValue) {
		return getIndexedPropertyMap().put(propertyName, newValue);
	}

	protected HashMap<String, List> getIndexedPropertyMap() {
		return this.indexedPropertyMap;
	}

}
