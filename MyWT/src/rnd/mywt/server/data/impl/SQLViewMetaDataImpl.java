package rnd.mywt.server.data.impl;

import rnd.mywt.server.data.SQLViewMetaData;

public class SQLViewMetaDataImpl extends ViewMetaDataImpl implements SQLViewMetaData {

	private String viewQuery;

	public void setViewQuery(String viewQuery) {
		this.viewQuery = viewQuery;
	}

	public String getViewQuery() {
		return this.viewQuery;
	}

}
