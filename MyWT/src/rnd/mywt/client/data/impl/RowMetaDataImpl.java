package rnd.mywt.client.data.impl;

import java.io.Serializable;

import rnd.mywt.client.data.ColumnMetaData;
import rnd.mywt.client.data.RowMetaData;

public class RowMetaDataImpl implements RowMetaData, Serializable {

	public RowMetaDataImpl() {
	}

	private ColumnMetaData[] columnMetaDatas;

	private int idColumnIndex;
	private int displayColumnIndex;

	private String moduleName;
	private String applicationBeanName;
	private String viewName;

	public RowMetaDataImpl(ColumnMetaData[] columnMetaDatas) {
		this.columnMetaDatas = columnMetaDatas;
	}

	public int getIdColumnIndex() {
		return this.idColumnIndex;
	}

	public void setIdColumnIndex(int idColumnIndex) {
		this.idColumnIndex = idColumnIndex;
	}

	public int getDisplayColumnIndex() {
		return this.displayColumnIndex;
	}

	public void setDisplayColumnIndex(int displayColumnIndex) {
		this.displayColumnIndex = displayColumnIndex;
	}

	public int getColumnCount() {
		return this.columnMetaDatas.length;
	}

	public ColumnMetaData[] getColumnMetaDatas() {
		return this.columnMetaDatas;
	}

	@Override
	public String getModuleName() {
		return moduleName;
	}

	@Override
	public String getApplicationBeanName() {
		return applicationBeanName;
	}

	@Override
	public String getViewName() {
		return viewName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public void setApplicationBeanName(String applicationBeanName) {
		this.applicationBeanName = applicationBeanName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

}
