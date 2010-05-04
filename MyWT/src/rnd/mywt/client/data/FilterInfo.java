package rnd.mywt.client.data;

import java.util.List;

import rnd.expression.Expression;

public interface FilterInfo {

	String getFilterName();

	void calculateFilterParams();

	List getFilterParams();

	Expression[] getFilterParamExpressions();

	Object[] getFilterParamExpressionObjects();

}
