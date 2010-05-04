package rnd.mywt.client.bean;

import java.util.Collection;
import java.util.Set;

public interface _Bean {

	// Property
	Set getPropertyNames();

	Object setValue(String propertyName, Object value);

	Object getValue(String propertyName);

	// Indexed Property

	Set getIndexedPropertyNames();

	Object setElement(String propertyName, int index, Object element);

	Object getElement(String propertyName, int index);

	int size(String propertyName);

	// Add
	boolean addElement(String propertyName, Object element);

	void addElement(String propertyName, int index, Object element);

	boolean addAllElement(String propertyName, Collection c);

	// Remove

	boolean removeElement(String propertyName, Object element);

	Object removeElement(String propertyName, int index);

	boolean removeAllElement(String propertyName, Collection c);

}