package rnd.mywt.client.expression;

import rnd.bean.ValueChangeListener;
import rnd.bean._Bean;
import rnd.bean._BoundBean;
import rnd.bean.expression.ValueExpression;
import rnd.expression.XChangeListener;

public class BeanPropertyExpression extends ValueExpression {

	public BeanPropertyExpression() {
	}

	public BeanPropertyExpression(String propertyName) {
		super(propertyName);
	}

	public Object getValue(Object object) {
		return ((_Bean) object).getValue(this.propertyName);
	}

	public void setValue(Object object, Object value) {
		((_Bean) object).setValue(this.propertyName, value);
	}

	public void addXChangeListener(Object object, XChangeListener xcl) {
		((_BoundBean) object).addValueChangeListener(this.propertyName, (ValueChangeListener) getXChangeListenerDelegate(xcl, false));
	}

	public void removeXChangeListener(Object object, XChangeListener xcl) {
		((_BoundBean) object).removeValueChangeListener(this.propertyName, (ValueChangeListener) getXChangeListenerDelegate(xcl, true));
	}

}
