package rnd.mywt.client.expression;

import rnd.expression.AbstractExpression;
import rnd.expression.Expression;
import rnd.expression.XChangeEvent;
import rnd.expression.XChangeListener;
import rnd.mywt.client.Context;
import rnd.mywt.client.bean.IndexedValueChangedEvent;
import rnd.mywt.client.bean.ValueChangeEvent;
import rnd.mywt.client.bean.ValueChangeListener;

public class ContextValueExpression extends AbstractExpression implements Expression {

	private String key;

	public ContextValueExpression(String key) {
		this.key = key;
	}

	@Override
	public void setValue(Object object, Object value) {
		((Context) object).setContext(key, value);
	}

	@Override
	public Object getValue(Object object) {
		return ((Context) object).getContext(key);
	}

	public void addXChangeListener(Object object, XChangeListener xcl) {
		((Context) object).addValueChangeListener(this.key, (ValueChangeListener) getXChangeListenerDelegate(xcl, false));
	}

	public void removeXChangeListener(Object object, XChangeListener xcl) {
		((Context) object).removeValueChangeListener(this.key, (ValueChangeListener) getXChangeListenerDelegate(xcl, true));
	}

	private class ContextValueExpressionValueChangeListener extends XChangeListenerDelegate implements ValueChangeListener {

		private ContextValueExpressionValueChangeListener(XChangeListener xcl) {
			super(xcl);
		}

		public void valueChanged(ValueChangeEvent vce) {
			delegate.stateChanged(new XChangeEvent(vce.getSource(), vce.getOldValue(), vce.getNewValue(), ContextValueExpression.this));
		}

		public void indexedValueChanged(IndexedValueChangedEvent ivce) {
			throw new UnsupportedOperationException("BeanPropertyExpression does not support IndexedValueChangedEvent");
		}
	}

	@Override
	protected XChangeListenerDelegate getNewXChangeListenerDelegate(XChangeListener xcl) {
		return new ContextValueExpressionValueChangeListener(xcl);
	}

}
