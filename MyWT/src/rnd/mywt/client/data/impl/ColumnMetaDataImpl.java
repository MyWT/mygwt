package rnd.mywt.client.data.impl;

import java.io.Serializable;

import rnd.expression.Expression;
import rnd.mywt.client.bean.DynaBean;
import rnd.mywt.client.data.ColumnMetaData;
import rnd.utils.WrapperUtils;

public class ColumnMetaDataImpl extends DynaBean implements ColumnMetaData, Serializable {

	public ColumnMetaDataImpl() {
	}

	public void setType(int dataType) {
		setValue(DATA_TYPE, dataType);
	}

	public void setDisplayName(String displayName) {
		setValue(DISPLAY_NAME, displayName);
	}

	public void setName(String name) {
		setValue(NAME, name);
	}

	public void setDisplayWidth(int width) {
		setValue(WIDTH, width);
	}

	public void setUpdatable(boolean updatable) {
		setValue(UPDATABLE, updatable);
	}

	public void setExpression(Expression expresion) {
		setValue(EXPRESSION, expresion);
	}

	public int getType() {
		return WrapperUtils.getInteger(getValue(DATA_TYPE));
	}

	public String getDisplayName() {
		return (String) getValue(DISPLAY_NAME);
	}

	public String getName() {
		return (String) getValue(NAME);
	}

	public int getDisplayWidth() {
		return WrapperUtils.getInteger(getValue(WIDTH));
	}

	public boolean isUpdatable() {
		return WrapperUtils.getBoolean(getValue(UPDATABLE));
	}

	public Expression getExpression() {
		return (Expression) getValue(EXPRESSION);
	}

}
