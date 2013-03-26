package rnd.mywt.client;

import rnd.mywt.client.bean.ValueChangeListener;

public interface Context {

	Object getContext(String key);

	Object setContext(String key, Object value);

	void addValueChangeListener(String propertyName, ValueChangeListener vcl);

	void removeValueChangeListener(String propertyName, ValueChangeListener vcl);

}
