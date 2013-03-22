package rnd.webapp.mygwtext.client.mvc.page.board;

import java.io.Serializable;

import rnd.mywt.client.Logger;
import rnd.mywt.client.MyWTHelper;
import rnd.mywt.client.arb.ARBAsyncCallback;
import rnd.mywt.client.data.Row;
import rnd.mywt.client.mvc.AbstractMVCBean;
import rnd.mywt.client.mvc.field.Table;
import rnd.mywt.client.mvc.field.Table.RowTableModel;
import rnd.mywt.client.mvc.field.data.ReferenceField;
import rnd.mywt.client.mvc.page.board.ActionBar;
import rnd.mywt.client.mvc.page.board.ActionBoard;
import rnd.mywt.client.mvc.page.board.DataBoard;
import rnd.mywt.client.mvc.page.board.FormBoard;
import rnd.mywt.client.mvc.page.form.Form.FormModel;
import rnd.mywt.client.rpc.ApplicationRequest;
import rnd.mywt.client.rpc.util.ARUtils;

import com.google.gwt.user.client.Window;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;

public class GWTExtActionBar extends AbstractMVCBean implements ActionBar {

	public GWTExtActionBar() {
		setView(new GWTExtActionBarView());
	}

	public ActionBoard getActionBoard() {
		return (ActionBoard) getParent();
	}

	private Toolbar actionBar;

	private Toolbar getToolbar() {
		if (this.actionBar == null) {
			this.actionBar = createToolbar();
		}
		return this.actionBar;
	}

	protected Toolbar createToolbar() {

		Toolbar toolBar = new Toolbar();

		// New
		ToolbarButton newButton = new ToolbarButton("New", new ButtonListenerAdapter() {
			@Override
			public void onClick(Button button, EventObject e) {
				try {
					performNewAction();
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		});
		toolBar.addButton(newButton);

		// Save
		ToolbarButton saveButton = new ToolbarButton("Save", new ButtonListenerAdapter() {
			@Override
			public void onClick(Button button, EventObject e) {

				try {
					performSaveAction();
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}

		});
		toolBar.addButton(saveButton);

		// Update
		ToolbarButton updateButton = new ToolbarButton("Update", new ButtonListenerAdapter() {
			@Override
			public void onClick(Button button, EventObject e) {
				try {

					DataBoard currentDataBoard = getCurrentDataBoard();

					Row currRow = ((Table.RowTableModel) currentDataBoard.getTable().getModel()).getCurrentRow();

					if (currRow == null) {
						Window.alert("No Row Selected");
						return;
					}

					// RowCacheImpl.get().addRow(currentDataBoard.getModuleName(),
					// currentDataBoard.getApplicationBeanName(),
					// currentDataBoard.getViewName(), currRow);

					performUpdateAction(currRow.getId(), currentDataBoard.getViewName());
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		});
		toolBar.addButton(updateButton);

		// Delete
		ToolbarButton deleteButton = new ToolbarButton("Delete", new ButtonListenerAdapter() {
			@Override
			public void onClick(Button button, EventObject e) {
				try {

					Row currRow = ((Table.RowTableModel) (getCurrentDataBoard()).getTable().getModel()).getCurrentRow();

					if (currRow == null) {
						Window.alert("No Row Selected");
						return;
					}

					performDeleteAction(currRow.getId());
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		});
		toolBar.addButton(deleteButton);

		ToolbarButton selectButton = new ToolbarButton("Select", new ButtonListenerAdapter() {
			@Override
			public void onClick(Button button, EventObject e) {
				try {

					performSelectAction();

				} catch (Throwable t) {
					t.printStackTrace();
				}
			}

		});
		toolBar.addButton(selectButton);

		// Refresh
		ToolbarButton refreshButton = new ToolbarButton("Refresh", new ButtonListenerAdapter() {
			@Override
			public void onClick(Button button, EventObject e) {
				try {
					performRefreshAction();
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}

		});
		toolBar.addButton(refreshButton);

		ToolbarButton closeButton = new ToolbarButton("Close", new ButtonListenerAdapter() {
			@Override
			public void onClick(Button button, EventObject e) {
				try {
					performCloseAction();
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		});
		toolBar.addButton(closeButton);

		// ToolbarButton testButton = new ToolbarButton("Test", new
		// ButtonListenerAdapter() {
		// @Override
		// public void onClick(Button button, EventObject e) {
		// try {
		//
		// }
		// catch (Throwable t) {
		// t.printStackTrace();
		// }
		// }
		// });
		// toolBar.addButton(testButton);

		return toolBar;
	}

	// View
	public class GWTExtActionBarView implements ActionBarView {
		public Object getViewObject() {
			return getToolbar();
		}
	}

	private void performNewAction() {
		createFormBoard(null, null);
	}

	private void performSaveAction() {

		final FormBoard formBoard = getCurrentFormBoard();
		FormModel formModel = (FormModel) formBoard.getForm().getModel();

		ApplicationRequest req = ARUtils.createSaveRequest(formBoard.getModuleName(), formBoard.getApplicationBeanName(), formBoard.getDataBoard().getViewName(), formModel.getApplicationBean());
		MyWTHelper.getARB().executeRequest(req, new ARBAsyncCallback() {

			@Override
			public void processResult(Serializable result) {

				try {
					Logger.startMethod("", "onSuccess");
					Window.alert("Object Saved Successfully");
					Long applicationBeanId = formBoard.getApplicationBeanId();
					Logger.log("applicationBeanId", applicationBeanId);

					Row savedRow = (Row) result;
					Logger.log("savedRow", savedRow);

					performCloseAction();
					DataBoard dataBoard = formBoard.getDataBoard();
					// (DataBoard)
					// getActionBoard().getActionBase().getBoard(getCurrentFormBoard().getModuleName(),
					// getCurrentFormBoard().getApplicationBeanName(),
					// BoardType.DATA_BOARD);
					if (applicationBeanId == null) {
						dataBoard.addRow(savedRow);
					} else {
						dataBoard.updateCurrentRow(savedRow);
					}

				} catch (RuntimeException e) {
					e.printStackTrace();
					throw e;
				} finally {
					Logger.endMethod("", "onSuccess");
				}

			}
		});

	}

	private void performUpdateAction(Long appBeanId, String viewName) {
		createFormBoard(appBeanId, viewName);
	}

	private void createFormBoard(Long appBeanId, String viewName) {

		DataBoard dataBoard = getCurrentDataBoard();
		FormBoard formBoard = MyWTHelper.getMVCFactory().createFormBoard(dataBoard.getModuleName(), dataBoard.getApplicationBeanName());
		formBoard.setApplicationBeanId(appBeanId);
		formBoard.setDataBoard(dataBoard);

		getActionBoard().getActionBase().addBoard(formBoard);
		getActionBoard().getActionBase().setCurrentBoard(formBoard);

	}

	private void performRefreshAction() {
		getCurrentDataBoard().refreshTable();
	}

	private void performDeleteAction(Long appBeanId) {

		boolean sure = Window.confirm("Are you sure to Delete");
		if (!sure) {
			return;
		}

		ApplicationRequest req = ARUtils.createDeleteRequest(getCurrentDataBoard().getModuleName(), getCurrentDataBoard().getApplicationBeanName(), appBeanId);
		MyWTHelper.getARB().executeRequest(req, new ARBAsyncCallback() {

			@Override
			public void processResult(Serializable result) {
				try {
					Window.alert("Object Delete Successfully");
					getCurrentDataBoard().removeCurrentRow();
				} catch (RuntimeException e) {
					e.printStackTrace();
					throw e;
				}
			}

		});
	}

	private void performCloseAction() {
		getActionBoard().getActionBase().removeCurrentBoard();
	}

	private void performSelectAction() {

		DataBoard currentDataBoard = getCurrentDataBoard();
		ReferenceField referenceField = currentDataBoard.getReferenceField();
		if (referenceField != null) {
			Row currRow = ((RowTableModel) currentDataBoard.getTable().getModel()).getCurrentRow();

			if (currRow == null) {
				Window.alert("No Row Selected");
				return;
			}

			referenceField.setReference(currRow);
			currentDataBoard.setReferenceField(null);

			FormBoard formBoard = (FormBoard) referenceField.getParent().getParent();
			formBoard.getActionBase().setCurrentBoard(formBoard);
		}
	}

	private FormBoard getCurrentFormBoard() {
		return (FormBoard) getActionBoard().getActionBase().getCurrentBoard();
	}

	private DataBoard getCurrentDataBoard() {
		return (DataBoard) getActionBoard().getActionBase().getCurrentBoard();
	}
}
