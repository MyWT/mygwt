package rnd.mywt.client.bean;

import java.util.EventObject;

public class ValueChangeEvent<T> extends EventObject {

	private String propertyName;

	private T oldValue;

	private T newValue;

	public ValueChangeEvent(Object source, String propertyName, T oldValue, T newValue) {
		super(source);
		this.propertyName = propertyName;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	public String getPropertyName() {
		return this.propertyName;
	}

	public T getOldValue() {
		return this.oldValue;
	}

	public T getNewValue() {
		return this.newValue;
	}

}
