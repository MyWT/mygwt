package rnd.mywt.client.bean;

public class IndexedValueChangedEvent<U> extends ValueChangeEvent<U> {

	private int index;

	public IndexedValueChangedEvent(Object source, String propertyName, U oldValue, U newValue, int index) {
		super(source, propertyName, oldValue, newValue);
		this.index = index;
	}

	public int getIndex() {
		return this.index;
	}

}
