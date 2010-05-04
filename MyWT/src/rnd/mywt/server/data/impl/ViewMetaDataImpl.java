package rnd.mywt.server.data.impl;

import java.util.HashMap;
import java.util.Map;

import rnd.mywt.server.data.ViewMetaData;

public class ViewMetaDataImpl implements ViewMetaData {

	private String viewName;

	private String[] viewColumnsNames;

	private String[] viewColumnsExpressions;

	private int idColumnIndex;

	private String idColumnName;

	private int displayColumnIndex;

	private String displayColumnName;

	private Map<String, String> filterMap;

	public void setIdColumnIndex(int idColumnIndex) {
		this.idColumnIndex = idColumnIndex;
	}

	public int getIdColumnIndex() {
		return this.idColumnIndex;
	}

	public String getViewName() {
		return this.viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public int getDisplayColumnIndex() {
		return displayColumnIndex;
	}

	public void setDisplayColumnIndex(int displayColumnIndex) {
		this.displayColumnIndex = displayColumnIndex;
	}

	public String getDisplayColumnName() {
		return displayColumnName;
	}

	public String getIdColumnName() {
		return idColumnName;
	}

	public void setDisplayColumnName(String displayColumnName) {
		this.displayColumnName = displayColumnName;
	}

	public void setIdColumnName(String idColumnName) {
		this.idColumnName = idColumnName;
	}

	public String getFilterExpression(String filterName) {
		if (filterMap == null) {
			return null;
		}
		return filterMap.get(filterName);
	}

	public void addFilter(String filterName, String filterExpression) {
		getFilterMap().put(filterName, filterExpression);
	}

	private Map<String, String> getFilterMap() {
		if (filterMap == null) {
			filterMap = new HashMap<String, String>();
		}
		return filterMap;
	}

	public String[] getViewColumnsNames() {
		return viewColumnsNames;
	}

	public void setViewColumnsNames(String[] viewColumnsNames) {
		this.viewColumnsNames = viewColumnsNames;
	}

	public String[] getViewColumnsExpressions() {
		return viewColumnsExpressions;
	}

	public void setViewColumnsExpressions(String[] viewColumnsExpressions) {
		this.viewColumnsExpressions = viewColumnsExpressions;
	}

}