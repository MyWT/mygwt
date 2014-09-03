package rnd.mywt.client.expression;

import rnd.bean.ValueChangeListener;
import rnd.bean.expression.ValueExpression;
import rnd.expression.XChangeListener;
import rnd.mywt.client.Context;

public class ContextValueExpression extends ValueExpression {

	public ContextValueExpression(String key) {
		super(key);
	}

	@Override
	public void setValue(Object object, Object value) {
		((Context) object).setContext(propertyName, value);
	}

	@Override
	public Object getValue(Object object) {
		return ((Context) object).getContext(propertyName);
	}

	public void addXChangeListener(Object object, XChangeListener xcl) {
		((Context) object).addValueChangeListener(this.propertyName, (ValueChangeListener) getXChangeListenerDelegate(xcl, true));
	}

	public void removeXChangeListener(Object object, XChangeListener xcl) {
		((Context) object).removeValueChangeListener(this.propertyName, (ValueChangeListener) getXChangeListenerDelegate(xcl, false));
	}

}
