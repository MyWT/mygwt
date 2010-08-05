package rnd.webapp.mygwtext.client.mvc.page.board;

import java.io.Serializable;

import rnd.expression.Expression;
import rnd.expression.XChangeEvent;
import rnd.expression.XChangeListener;
import rnd.mywt.client.Logger;
import rnd.mywt.client.bean.ValueChangeEvent;
import rnd.mywt.client.bean.ValueChangeListenerAdapter;
import rnd.mywt.client.data.ColumnMetaData;
import rnd.mywt.client.data.DataTable;
import rnd.mywt.client.data.FilterInfo;
import rnd.mywt.client.data.Row;
import rnd.mywt.client.mvc.MVCHandlerFactory;
import rnd.mywt.client.mvc.field.Table;
import rnd.mywt.client.mvc.field.Table.RowTableModel;
import rnd.mywt.client.mvc.field.data.ReferenceField;
import rnd.mywt.client.mvc.page.board.DataBoard;
import rnd.mywt.client.rpc.util.ARUtils;
import rnd.utils.WrapperUtils;
import rnd.webapp.mygwt.client.ARBAsyncCallback;
import rnd.webapp.mygwt.client.ARBUtils;

import com.gwtext.client.widgets.Panel;

public class GWTExtDataBoard extends GWTExtBoard implements DataBoard {

	private Table dataTable;

	private ReferenceField referenceField;

	public GWTExtDataBoard(String moduleName, String appBeanName, String viewName) {
		super(moduleName, appBeanName, viewName);
		setModel(new GWTExtDataBoardModel());
		setView(new GWTExtDataBoardView());
	}

	public BoardType getBoardType() {
		return BoardType.DATA_BOARD;
	}

	public ReferenceField getReferenceField() {
		return this.referenceField;
	}

	public void setReferenceField(ReferenceField referenceField) {
		this.referenceField = referenceField;
	}

	public Table getDataTable() {
		if (this.dataTable == null) {
			this.dataTable = createTable();
		}
		return this.dataTable;
	}

	public void refreshDataTable() {
		if (((DataBoardModel) getModel()).isDataTableMetaDataIntialized()) {
			fetchDataTable(false);
		}
	}

	private Table createTable() {
		Table newDataTable = MVCHandlerFactory.getMVCHandler().createTable(Table.ROW_BASED);
		newDataTable.setParent(this);
		fetchDataTable(true);
		return newDataTable;
	}

	private void fetchDataTable(final boolean refreshMetaData) {
		// Logger.startMethod("GWTExtDataBoard", "fetchDataTable");
		FilterInfo filterInfo = getFilter();
		if (filterInfo != null) {
			filterInfo.calculateFilterParams();
		}

		// final RuntimeException re = new RuntimeException("outer");

		// Logger.log("getFilter()", getFilter());
		((DataBoardModel) getModel()).setDataTableIntialized(false);

		ARBUtils.getARB().executeRequest(ARUtils.createFetchRequest(getModuleName(), getApplicationBeanName(), getViewName(), filterInfo), new ARBAsyncCallback() {

			@Override
			public void onSuccess(Serializable result) {
				// re.printStackTrace();
				// new RuntimeException("inner").printStackTrace();

				try {
					DataTable rowDataTable = (DataTable) result;

					if (refreshMetaData) {
						((DataBoardModel) getModel()).setDataTableMetaDataIntialized(false);
						ColumnMetaData[] columnMetaData = rowDataTable.getRowMetaData().getColumnMetaDatas();
						dataTable.setColumnMetaDatas(columnMetaData);
						((DataBoardModel) getModel()).setDataTableMetaDataIntialized(true);
					}

					((Table.RowTableModel) dataTable.getModel()).setDataTable(rowDataTable);
					((DataBoardModel) getModel()).setDataTableIntialized(true);
				} catch (RuntimeException e) {
					e.printStackTrace();
					throw e;
				}
			}
		});

		// Logger.endMethod("GWTExtDataBoard", "fetchDataTable");
	}

	public void addRow(Row newRow) {
		((RowTableModel) getDataTable().getModel()).addRow(newRow);
	}

	public void removeCurrentRow() {
		((RowTableModel) getDataTable().getModel()).removeCurrentRow();
	}

	public void updateCurrentRow(Row updatedRow) {
		Logger.startMethod("GWTExtDataBoard", "updateCurrentRow");
		((RowTableModel) getDataTable().getModel()).updateCurrentRow(updatedRow);
		Logger.endMethod("GWTExtDataBoard", "updateCurrentRow");
	}

	public FilterInfo getFilter() {
		return (FilterInfo) getValue(FILTER);
	}

	public void setFilter(FilterInfo filterInfo) {
		// Logger.startMethod("GWTExtDataBoard", "setFilter");
		// Logger.log("filterInfo", filterInfo);
		setValue(FILTER, filterInfo);
		// Logger.endMethod("GWTExtDataBoard", "setFilter");
	}

	public class GWTExtDataBoardModel implements DataBoardModel {

		private DataBoardRefresher dataBoardRefresher = new DataBoardRefresher();

		private class DataBoardRefresher implements XChangeListener {
			public void stateChanged(XChangeEvent changeEvent) {
				refreshDataTable();
			}
		}

		public boolean isDataTableIntialized() {
			return WrapperUtils.getBoolean(getValue(DATA_TABLE_INTIALIZED));
		}

		public void setDataTableIntialized(boolean dataTableIntialized) {
			setValue(DATA_TABLE_INTIALIZED, dataTableIntialized);
		}

		public boolean isDataTableMetaDataIntialized() {
			return WrapperUtils.getBoolean(getValue(DATA_TABLE_META_DATA_INTIALIZED));
		}

		public void setDataTableMetaDataIntialized(boolean dataTableMetaDataIntialized) {
			setValue(DATA_TABLE_META_DATA_INTIALIZED, dataTableMetaDataIntialized);
		}

		public boolean isFilterReset() {
			return WrapperUtils.getBoolean(getValue(FILTER_RESET));
		}

		public void setFilterReset(boolean filterReset) {
			setValue(FILTER_RESET, filterReset);
		}

		public GWTExtDataBoardModel() {

			addValueChangeListener(DATA_TABLE_META_DATA_INTIALIZED, new ValueChangeListenerAdapter<Boolean>() {
				public void valueChanged(ValueChangeEvent<Boolean> vce) {
					if (isDataTableMetaDataIntialized()) {
						if (isFilterReset()) {
							refreshDataTable();
							setFilterReset(false);
						}
					}
				}
			});

			addValueChangeListener(DATA_TABLE_INTIALIZED, new ValueChangeListenerAdapter<Boolean>() {
				public void valueChanged(ValueChangeEvent<Boolean> vce) {
					if (isDataTableIntialized()) {
						if (referenceField != null) {
							if (referenceField.getText() != null && referenceField.getText().trim().length() != 0) {
								Object search = referenceField.search(referenceField.getText());
								if (search == null) {
									referenceField.setValue(null);
								}
							}
						}
					}
				}
			});

			addValueChangeListener(FILTER, new ValueChangeListenerAdapter<FilterInfo>() {

				public void valueChanged(ValueChangeEvent<FilterInfo> vce) {
					setFilterReset(true);
					refreshDataTable();

					FilterInfo oldFilterInfo = vce.getOldValue();
					if (oldFilterInfo != null) {

						Object[] filterParamExpressionObjects = oldFilterInfo.getFilterParamExpressionObjects();
						Expression[] filterParamExpressions = oldFilterInfo.getFilterParamExpressions();

						for (int i = 0; i < filterParamExpressions.length; i++) {
							Expression expression = filterParamExpressions[i];
							expression.removeXChangeListener(filterParamExpressionObjects[i], dataBoardRefresher);
						}
					}

					FilterInfo newFilterInfo = vce.getNewValue();
					if (newFilterInfo != null) {

						Object[] filterParamExpressionObjects = newFilterInfo.getFilterParamExpressionObjects();
						Expression[] filterParamExpressions = newFilterInfo.getFilterParamExpressions();

						for (int i = 0; i < filterParamExpressions.length; i++) {
							Expression expression = filterParamExpressions[i];
							expression.addXChangeListener(filterParamExpressionObjects[i], dataBoardRefresher);
						}
					}

				}

			});
		}
	}

	public class GWTExtDataBoardView implements DataBoardView {

		public Object getViewObject() {
			Panel boardPanel = (Panel) getDataTable().getView().getViewObject();
			boardPanel.setTitle(getViewName());
			return boardPanel;
		}
	}

}