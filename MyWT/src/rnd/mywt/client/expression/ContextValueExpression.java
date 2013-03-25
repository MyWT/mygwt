package rnd.mywt.client.expression;

import rnd.expression.AbstractExpression;
import rnd.expression.Expression;
import rnd.mywt.client.Context;

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
}
