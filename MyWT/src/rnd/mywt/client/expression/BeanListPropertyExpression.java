package rnd.mywt.client.expression;

import rnd.bean.BeanPropertyList;
import rnd.bean._BoundBean;

public class BeanListPropertyExpression extends BeanPropertyExpression {

	// private String inverseOwnerPropName;

	public BeanListPropertyExpression() {
	}

	public BeanListPropertyExpression(String propertyName) {
		this(propertyName, null);
	}

	public BeanListPropertyExpression(String propertyName, String inverseOwnerPropName) {
		super(propertyName);
		// this.inverseOwnerPropName = inverseOwnerPropName;
	}

	@Override
	public Object getValue(Object object) {
		// return new BeanPropertyList((_BoundBean) object, this.propertyName, this.inverseOwnerPropName);
		return new BeanPropertyList((_BoundBean) object, this.propertyName);
	}

	@Override
	public void setValue(Object object, Object value) {
		throw new UnsupportedOperationException("Set Value Not Supported for _Bean's List Property Expression.");
	}

}
