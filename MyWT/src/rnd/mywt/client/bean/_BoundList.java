package rnd.mywt.client.bean;

import java.util.List;

public interface _BoundList extends List {

	void addValueChangeListner(ValueChangeListener vcl);

	void removeValueChangeListner(ValueChangeListener vcl);
}