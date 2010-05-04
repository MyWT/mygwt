package rnd.mywt.client.data.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import rnd.expression.Expression;
import rnd.mywt.client.data.FilterInfo;

public class FilterInfoImpl implements FilterInfo, Serializable {

	public FilterInfoImpl() {
	}

	public FilterInfoImpl(String filterName) {
		this.filterName = filterName;
	}

	private String filterName;

	private List filterParams = new ArrayList();

	private transient Expression[] filterParamExpressions;

	private transient Object[] filterParamExpressionObjects;

	public String getFilterName() {
		return filterName;
	}

	public List getFilterParams() {
		return filterParams;
	}

	public Object[] getFilterParamExpressionObjects() {
		return filterParamExpressionObjects;
	}

	public Expression[] getFilterParamExpressions() {
		return filterParamExpressions;
	}

	public void setFilterParamExpressions(Expression... filterParamExpressions) {
		this.filterParamExpressions = filterParamExpressions;
	}

	public void setFilterParamExpressionObjects(Object... filterParamExpressionObjects) {
		this.filterParamExpressionObjects = filterParamExpressionObjects;
	}

	public void calculateFilterParams() {
		filterParams.clear();
		for (int i = 0; i < filterParamExpressions.length; i++) {
			filterParams.add(filterParamExpressions[i].getValue(filterParamExpressionObjects[i]));
		}
	}
}
