package rnd.mywt.client;

import rnd.bean.ValueChangeListener;
import rnd.bean._Bean;

public interface Context {

	Object getContext(String key);

	Object setContext(String key, Object value);

	void addValueChangeListener(String propertyName, ValueChangeListener vcl);

	void removeValueChangeListener(String propertyName, ValueChangeListener vcl);

	_Bean getContextBean();

}
