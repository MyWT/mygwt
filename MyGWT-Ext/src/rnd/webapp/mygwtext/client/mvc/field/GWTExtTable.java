package rnd.webapp.mygwtext.client.mvc.field;

import java.util.Collection;
import java.util.logging.Logger;

import rnd.mywt.client.bean.IndexedValueChangedEvent;
import rnd.mywt.client.bean.ValueChangeListenerAdapter;
import rnd.mywt.client.bean._Bean;
import rnd.mywt.client.bean._BoundList;
import rnd.mywt.client.data.ColumnMetaData;
import rnd.mywt.client.data.DataTable;
import rnd.mywt.client.mvc.AbstractMVCBean;
import rnd.mywt.client.mvc.field.Table;

import com.google.gwt.dev.generator.ast.Expression;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.data.BooleanFieldDef;
import com.gwtext.client.data.DateFieldDef;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.FloatFieldDef;
import com.gwtext.client.data.IntegerFieldDef;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.form.Checkbox;
import com.gwtext.client.widgets.form.DateField;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.NumberField;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.grid.BaseColumnConfig;
import com.gwtext.client.widgets.grid.CellSelectionModel;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.EditorGridPanel;
import com.gwtext.client.widgets.grid.GridEditor;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.RowNumberingColumnConfig;
import com.gwtext.client.widgets.grid.RowSelectionModel;
import com.gwtext.client.widgets.grid.event.EditorGridListenerAdapter;
import com.gwtext.client.widgets.grid.event.GridListenerAdapter;
import com.gwtext.client.widgets.grid.event.RowSelectionListenerAdapter;
import com.gwtext.client.widgets.layout.FitLayout;
import com.sun.rowset.internal.Row;
import com.sun.xml.internal.ws.wsdl.writer.document.Types;

public class GWTExtTable extends AbstractMVCBean implements Table {

	private EditorGridPanel grid;

	private RecordDef recordDef;

	private Store store;

	public GWTExtTable(int tableType) {
		if (tableType == BEAN_BASED) {
			setModel(new GWTExtBeanTableModel());
			setView(new GWTExtBeanTableView());
		} else {
			setModel(new GWTExtRowTableModel());
			setView(new GWTExtTableView());
		}

		addValueChangeListener(COLUMN_META_DATA, new ValueChangeListenerAdapter() {
			public void valueChanged(ValueChangeEvent vce) {
				Panel panel = ((Panel) getView().getViewObject());
				panel.removeAll();
				createGrid();
				panel.add(grid);
				panel.doLayout();
			}
		});

	}

	public ColumnMetaData[] getColumnMetaDatas() {
		return (ColumnMetaData[]) getValue(COLUMN_META_DATA);
	}

	public void setColumnMetaDatas(ColumnMetaData[] columnMetaDatas) {
		setValue(COLUMN_META_DATA, columnMetaDatas);
	}

	public String getFieldProperty() {
		return getModel() instanceof BeanTableModel ? BeanTableModel.DATA_LIST : RowTableModel.DATA_TABLE;
	}

	protected EditorGridPanel getGrid() {
		// if (this.grid == null) {
		// createGrid();
		// }
		return this.grid;
	}

	protected TableModel getTableModel() {
		return (TableModel) getModel();
	}

	protected TableView getTableView() {
		return (TableView) getView();
	}

	protected void createGrid() {

		this.grid = new EditorGridPanel();
		this.grid.setClicksToEdit(1);

		this.grid.setHeight(100);

		int width = 27;

		ColumnMetaData[] tableColumnMetadatas = getColumnMetaDatas();

		BaseColumnConfig[] bccs = new BaseColumnConfig[tableColumnMetadatas.length + 1];
		bccs[0] = new RowNumberingColumnConfig();

		FieldDef[] fds = new FieldDef[tableColumnMetadatas.length];

		for (int i = 0; i < tableColumnMetadatas.length; i++) {

			int displayWidth = tableColumnMetadatas[i].getDisplayWidth();
			if (displayWidth <= 100) {
				displayWidth *= 4;
			}
			width += displayWidth;
			ColumnConfig cc = new ColumnConfig(tableColumnMetadatas[i].getDisplayName(), tableColumnMetadatas[i].getName(), displayWidth, false);
			bccs[i + 1] = cc;

			if (tableColumnMetadatas[i].isUpdatable()) {

				Field editorField = null;

				switch (tableColumnMetadatas[i].getType()) {
					case Types.VARCHAR:
						editorField = new TextField();
						break;
					case Types.INTEGER:
					case Types.FLOAT:
					case Types.BIGINT:
						editorField = new NumberField();
						break;
					case Types.DATE:
						editorField = new DateField();
						break;
					case Types.BOOLEAN:
					case Types.BIT:
						editorField = new Checkbox();
						break;
				}
				cc.setEditor(new GridEditor(editorField));
			}

			FieldDef fd = null;

			switch (tableColumnMetadatas[i].getType()) {
				case Types.VARCHAR:
					fd = new StringFieldDef(tableColumnMetadatas[i].getName());
					break;
				case Types.INTEGER:
				case Types.BIGINT:
					fd = new IntegerFieldDef(tableColumnMetadatas[i].getName());
					break;
				case Types.FLOAT:
					fd = new FloatFieldDef(tableColumnMetadatas[i].getName());
					break;
				case Types.DATE:
					fd = new DateFieldDef(tableColumnMetadatas[i].getName());
					break;
				case Types.BOOLEAN:
				case Types.BIT:
					fd = new BooleanFieldDef(tableColumnMetadatas[i].getName());
					break;
			}
			fds[i] = fd;

		}

		this.grid.setWidth(width);

		ColumnModel cm = new ColumnModel(bccs);
		this.grid.setColumnModel(cm);

		this.recordDef = new RecordDef(fds);
		this.setStore(new Store(this.recordDef));
		this.grid.setStore(this.getStore());

		getTableModel().initialiseListner();
		getTableView().initialiseListner();
	}

	public void setStore(Store s) {
		this.store = s;
	}

	public Store getStore() {
		return this.store;
	}

	public class GWTExtTableModel implements TableModel {

		public boolean isUpdatable() {
			return true;
		}

		public void initialiseListner() {
		}

		protected void removeRowInternal(int index) {
			// Logger.startMethod("GWTExtTableModel", "removeRowInternal");
			// Logger.log("index", index);

			Record row = getStore().getAt(index);
			// Logger.log("row", row);
			// Logger.log("Store.Count", getStore().getCount());
			if (row != null) {
				getStore().remove(row);
				getStore().commitChanges();
			}
			// Logger.log("Store.Count", getStore().getCount());
			// Logger.endMethod("GWTExtTableModel", "removeRowInternal");
		}

		protected void addRowInternal(int index, Object row) {
			ColumnMetaData[] tableColumnMetaData = getColumnMetaDatas();

			Object[] rowData = new Object[tableColumnMetaData.length];
			for (int i = 0; i < tableColumnMetaData.length; i++) {
				rowData[i] = tableColumnMetaData[i].getExpression().getValue(row);
			}

			Record r = recordDef.createRecord(rowData);
			getStore().insert(index, r);
			getStore().commitChanges();
		}

	}

	public class GWTExtBeanTableModel extends GWTExtTableModel implements BeanTableModel {

		private RowManager rowManager;

		public GWTExtBeanTableModel() {

			addValueChangeListener(DATA_LIST, new ValueChangeListenerAdapter() {
				@Override
				public void valueChanged(ValueChangeEvent vce) {

					_BoundList oldDataList = (_BoundList) vce.getOldValue();
					removeRowManager(oldDataList);

					_BoundList newDataList = (_BoundList) vce.getNewValue();
					addRowManager(newDataList);

				}
			});
		}

		public void initialiseListner() {

			grid.addEditorGridListener(new EditorGridListenerAdapter() {

				@Override
				public void onAfterEdit(GridPanel grid1, Record record, String field, Object newValue, Object oldValue, int rowIndex, int colIndex) {
					getColumnMetaDatas()[colIndex - 1].getExpression().setValue(getDataList().get(rowIndex), newValue);
					record.commit();
				}
			});
		}

		private class RowManager extends ValueChangeListenerAdapter {

			private ColumnManager columnManager = new ColumnManager();

			class ColumnManager implements XChangeListener {

				public void stateChanged(XChangeEvent xChangeEvent) {
					Record r = getStore().getRecordAt(getDataList().indexOf(xChangeEvent.getSource()));
					r.set(getColumnMetaData(xChangeEvent.getExpression()).getName(), xChangeEvent.getNewValue());
					// r.set(((BeanPropertyExpression) xChangeEvent.getExpression()).getPropertyName(), xChangeEvent.getNewValue());
					r.commit();
				}

				private ColumnMetaData getColumnMetaData(Expression expression) {
					ColumnMetaData[] columnMetaDatas = getColumnMetaDatas();
					for (int i = 0; i < columnMetaDatas.length; i++) {
						if (columnMetaDatas[i].getExpression() == expression) { return columnMetaDatas[i]; }
					}
					return null;
				}
			}

			@Override
			public void indexedValueChanged(IndexedValueChangedEvent ivce) {

				_Bean oldValue = (_Bean) ivce.getOldValue();
				if (oldValue != null) {
					rowRemoved(ivce.getIndex(), oldValue);
				}

				_Bean newValue = (_Bean) ivce.getNewValue();
				if (newValue != null) {
					rowAdded(ivce.getIndex(), newValue);
				}
			}

			void rowRemoved(int index, _Bean oldValue) {
				removeRowInternal(index);
				removeColumnManager(oldValue);
			}

			void rowAdded(int index, _Bean newValue) {
				addRowInternal(index, newValue);
				addColumnManager(newValue);
			}

			public ColumnManager getColumnManager() {
				return this.columnManager;
			}

		}

		public _BoundList getDataList() {
			return (_BoundList) getValue(DATA_LIST);
		}

		public void setDataList(_BoundList dataList) {
			setValue(DATA_LIST, dataList);
		}

		public RowDelegate getRowDelegate() {
			return (RowDelegate) getValue(ROW_DELEGATE);
		}

		public void setRowDelegate(RowDelegate rowDelegate) {
			setValue(ROW_DELEGATE, rowDelegate);
		}

		void addColumnManager(_Bean newValue) {

			ColumnMetaData[] tableColumnMetaDatas = getColumnMetaDatas();

			for (int i = 0; i < tableColumnMetaDatas.length; i++) {
				ColumnMetaData tableColumnMetaData = tableColumnMetaDatas[i];
				tableColumnMetaData.getExpression().addXChangeListener(newValue, getRowManager().getColumnManager());
			}
		}

		void removeColumnManager(_Bean oldValue) {

			ColumnMetaData[] columnMetaDatas = getColumnMetaDatas();
			for (int i = 0; i < columnMetaDatas.length; i++) {
				ColumnMetaData columnMetaData = columnMetaDatas[i];
				columnMetaData.getExpression().removeXChangeListener(oldValue, getRowManager().getColumnManager());
			}
		}

		void addRowManager(_BoundList newValue) {

			if (newValue != null) {

				// Self fire Row Added for existing Rows
				for (int i = 0; i < newValue.size(); i++) {
					_Bean row = (_Bean) newValue.get(i);
					getRowManager().rowAdded(i, row);
				}

				newValue.addValueChangeListner(getRowManager());
			}
		}

		void removeRowManager(_BoundList oldValue) {
			if (oldValue != null) {
				// Self fire Row removed for existing Rows
				for (int i = 0; i < oldValue.size(); i++) {
					_Bean row = (_Bean) oldValue.get(i);
					getRowManager().rowRemoved(i, row);
				}
				oldValue.removeValueChangeListner(getRowManager());
			}
		}

		public void insertRow() {
			Object newRow = getRowDelegate().newRow();
			int index = getDataList().size();
			addRow(newRow, index);
		}

		private void addRow(Object newValue, int index) {
			getDataList().add(index, newValue);
			((TableView) getView()).refresh();
		}

		public void removeRow(int index) {
			getDataList().remove(index);
		}

		private RowManager getRowManager() {
			if (this.rowManager == null) {
				this.rowManager = createRowManager();
			}
			return this.rowManager;
		}

		private RowManager createRowManager() {
			return new RowManager();
		}

	}

	public class GWTExtRowTableModel extends GWTExtTableModel implements RowTableModel {

		public GWTExtRowTableModel() {

			addValueChangeListener(DATA_TABLE, new ValueChangeListenerAdapter() {
				@Override
				public void valueChanged(ValueChangeEvent vce) {

					// Remove Old Row
					DataTable oldDataTable = (DataTable) vce.getOldValue();
					if (oldDataTable != null) {
						int rowCount = oldDataTable.getRowCount();
						for (int index = rowCount; index >= 0; index--) {
							removeRowInternal(index);
						}
					}

					// Add New Row
					DataTable newDataTable = (DataTable) vce.getNewValue();
					if (newDataTable != null) {
						Collection<Row> rows = newDataTable.getRows();
						int index = 0;
						for (Row row : rows) {
							addRowInternal(index++, row);
						}
					}
				}
			});
		}

		public boolean isUpdatable() {
			return false;
		}

		public DataTable getDataTable() {
			return (DataTable) getValue(DATA_TABLE);
		}

		public void setDataTable(DataTable dataTable) {
			setValue(DATA_TABLE, dataTable);
		}

		public Row getCurrentRow() {
			return (Row) getValue(CURRENT_ROW);
		}

		private void setCurrentRow(Row currentRow) {
			// Logger.startMethod("GWTExtRowTableModel", "setCurrentRow");
			// Logger.log("currentRow", currentRow);
			setValue(RowTableModel.CURRENT_ROW, currentRow);
			// Logger.endMethod("GWTExtRowTableModel", "setCurrentRow");
		}

		public void initialiseListner() {

			GWTExtTable.this.grid.getSelectionModel().addListener(new RowSelectionListenerAdapter() {

				public void onRowDeselect(RowSelectionModel sm, int rowIndex, Record record) {
					setCurrentRow(null);
				}

				public void onRowSelect(RowSelectionModel sm, int rowIndex, Record record) {
					setCurrentRow(getDataTable().getRow(rowIndex));
				}

			});

		}

		public void addRow(int index, Row newRow) {
			getDataTable().addRow(index, newRow);
			addRowInternal(index, newRow);
		}

		public void removeRow(int index) {
			// Logger.startMethod("GWTExtRowTableModel", "removeRow");
			// Logger.log("index", index);
			getDataTable().removeRow(index);
			removeRowInternal(index);
			// Logger.endMethod("GWTExtRowTableModel", "removeRow");
		}

		public void addRow(Row newRow) {
			addRow(getDataTable().getRowCount(), newRow);
		}

		public void removeRow(Row row) {
			removeRow(getDataTable().getRows().indexOf(row));
		}

		public void removeCurrentRow() {
			removeRow(getCurrentRow());
		}

		public void updateRow(Row oldRow, Row newRow) {
			Logger.startMethod("GWTExtRowTableModel", "updateRow");
			int index = getDataTable().getRows().indexOf(oldRow);
			Logger.log("index", index);

			removeRow(index);

			addRow(index, newRow);

			Logger.endMethod("GWTExtRowTableModel", "updateRow");
		}

		public void updateCurrentRow(Row newRow) {
			Logger.startMethod("GWTExtRowTableModel", "updateCurrentRow");
			Row currentRow = getCurrentRow();

			Logger.log("currentRow", currentRow);

			updateRow(currentRow, newRow);
			Logger.endMethod("GWTExtRowTableModel", "updateCurrentRow");
		}

	}

	public class GWTExtTableView implements TableView {
		Panel gridBase;

		public Object getViewObject() {
			if (this.gridBase == null) {
				this.gridBase = new Panel();
				this.gridBase.setLayout(new FitLayout());
				this.gridBase.setBorder(false);
				// this.gridBase.setWidth("100%");
				// this.gridBase.setHeight("100%");
			}
			return this.gridBase;
		}

		public void refresh() {
			getGrid().getView().refresh();
		}

		public void initialiseListner() {
		}

	}

	public class GWTExtBeanTableView extends GWTExtTableView implements BeanTableView {

		public void initialiseListner() {

			grid.addGridListener(new GridListenerAdapter() {
				@Override
				public void onKeyPress(EventObject e) {
					try {
						char key = (char) e.getKey();
						switch (key) {
							case 'e': // 101
							case 'E': // 69
								editSelectedCell();
								break;
							case 4: // Ctrl + D
								deleteSelectedRow();
								break;
							case 9:// Ctrl + I
								insertRow();
								break;
						}
					}
					catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
		}

		private void insertRow() {
			if (getTableModel().isUpdatable()) {
				((BeanTableModel) getModel()).insertRow();
			}
		}

		private void editSelectedCell() {
			if (getTableModel().isUpdatable()) {
				final CellSelectionModel cs = getGrid().getCellSelectionModel();
				int sc[] = cs.getSelectedCell();
				int r1 = sc[0];
				int c1 = sc[1];
				editCellAt(r1, c1);
			}
		}

		private void editCellAt(int rowIndex, int colIndex) {

			if (getColumnMetaDatas()[colIndex].isUpdatable()) {
				CellSelectionModel cs = getGrid().getCellSelectionModel();
				if (!cs.isLocked()) {
					cs.lock();
					getGrid().startEditing(rowIndex, colIndex);
					cs.unlock();
				}
			}
		}

		private void deleteSelectedRow() {
			// Logger.startMethod("GWTExtBeanTableView", "deleteSelectedRow");

			if (getTableModel().isUpdatable()) {

				final CellSelectionModel cs = getGrid().getCellSelectionModel();
				int cell[] = cs.getSelectedCell();
				// Logger.log("cell", cell);

				if (cell != null) {
					int r1 = cell[0];
					int c1 = cell[1];

					// Logger.log("r1", r1);
					// Logger.log("c1", c1);

					int cnt = getStore().getCount();
					// Logger.log("cnt", cnt);

					((BeanTableModel) getModel()).removeRow(r1);

					refresh();

					if ((r1 + 1) == cnt) {
						r1 -= 1;
					}
					if (r1 != -1) {
						cs.select(r1, c1);
					}
				}
			}
			// Logger.endMethod("GWTExtBeanTableView", "deleteSelectedRow");
		}

	}
}
