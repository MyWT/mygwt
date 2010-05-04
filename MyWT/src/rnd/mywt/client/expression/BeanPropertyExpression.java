package rnd.mywt.client.expression;

import rnd.expression.PropertyExpression;
import rnd.expression.XChangeEvent;
import rnd.expression.XChangeListener;
import rnd.mywt.client.bean.IndexedValueChangedEvent;
import rnd.mywt.client.bean.ValueChangeEvent;
import rnd.mywt.client.bean.ValueChangeListener;
import rnd.mywt.client.bean._Bean;
import rnd.mywt.client.bean._BoundBean;

public class BeanPropertyExpression extends PropertyExpression {

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

	private class BeanPropertyExpressionValueChangeListener extends XChangeListenerDelegate implements ValueChangeListener {

		BeanPropertyExpressionValueChangeListener(XChangeListener xcl) {
			super(xcl);
		}

		public void valueChanged(ValueChangeEvent vce) {
			BeanPropertyExpression.this.valueChanged(this.delegate, vce);
		}

		public void indexedValueChanged(IndexedValueChangedEvent ivce) {
			throw new UnsupportedOperationException("BeanPropertyExpression does not support IndexedValueChangedEvent");
		}
	}

	@Override
	protected XChangeListenerDelegate getNewXChangeListenerDelegate(XChangeListener xcl) {
		return new BeanPropertyExpressionValueChangeListener(xcl);
	}

	protected void valueChanged(XChangeListener xcl, ValueChangeEvent vce) {
//		Logger.startMethod("BeanPropertyExpression", "valueChanged");
		xcl.stateChanged(new XChangeEvent(vce.getSource(), vce.getOldValue(), vce.getNewValue(), this));
//		Logger.endMethod("BeanPropertyExpression", "valueChanged");
	}

}
