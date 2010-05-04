package rnd.mywt.client.bean;

public interface _ConstrainedBean extends _BoundBean {

	void addVetoableChangeListener(VetoableChangeListener vcl);

	void removeVetoableChangeListener(VetoableChangeListener vcl);

	void addVetoableChangeListener(String propertyName, VetoableChangeListener vcl);

	void removeVetoableChangeListener(String propertyName, VetoableChangeListener vcl);

}
