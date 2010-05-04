package rnd.mywt.client.bean;

import java.util.EventListener;

public interface ValueChangeListener<T> extends EventListener {

	void valueChanged(ValueChangeEvent<T> vce);

	void indexedValueChanged(IndexedValueChangedEvent<T> ivce);
}
