package rnd.mywt.client.bean.action;

import java.util.ArrayList;

public class ValueChangeActionDescriptor extends ArrayList<ValueChangeActionInfo> {
	public void addValueChangeAction(String propertyName, ValueChangeAction valueChangeAction) {
		addValueChangeAction(null, propertyName, valueChangeAction);
	}

	public void addValueChangeAction(String chain, String propertyName, ValueChangeAction valueChangeAction) {
		add(new ValueChangeActionInfo(chain, propertyName, valueChangeAction));
	}
}
