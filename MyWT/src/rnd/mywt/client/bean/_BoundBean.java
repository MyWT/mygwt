package rnd.mywt.client.bean;


public interface _BoundBean extends _Bean {

	void addValueChangeListener(String propertyName, ValueChangeListener vcl);

	void removeValueChangeListener(String propertyName, ValueChangeListener vcl);

}
