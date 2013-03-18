package rnd.webapp.mygwtext.client.mvc.page.board;

import java.io.Serializable;

import rnd.mywt.client.MyWTHelper;
import rnd.mywt.client.application.FormHelper;
import rnd.mywt.client.application.ModuleHelper;
import rnd.mywt.client.bean.ApplicationBean;
import rnd.mywt.client.data.Row;
import rnd.mywt.client.data.RowCacheImpl;
import rnd.mywt.client.mvc.page.board.ActionBase;
import rnd.mywt.client.mvc.page.board.DataBoard;
import rnd.mywt.client.mvc.page.board.FormBoard;
import rnd.mywt.client.mvc.page.form.Form;
import rnd.mywt.client.mvc.page.form.Form.FormModel;
import rnd.mywt.client.rpc.ApplicationRequest;
import rnd.mywt.client.rpc.util.ARUtils;
import rnd.webapp.mygwt.client.ARBAsyncCallback;
import rnd.webapp.mygwt.client.ARBUtils;

import com.gwtext.client.widgets.Panel;

public class GWTExtFormBoard extends GWTExtBoard implements FormBoard {

	private Form form;
	private DataBoard dataBoard;

	public GWTExtFormBoard(String moduleName, String appBeanName) {
		super(moduleName, appBeanName);
		setView(new GWTExtFormBoardView());
	}

	public Form getForm() {
		if (this.form == null) {
			this.form = createForm();
		}
		return this.form;
	}

	@Override
	public DataBoard getDataBoard() {
		return dataBoard;
	}

	public void setDataBoard(DataBoard dataBoard) {
		this.dataBoard = dataBoard;
	}

	public BoardType getBoardType() {
		return BoardType.FORM_BOARD;
	}

	private Form createForm() {

		ModuleHelper moduleHelper = MyWTHelper.getApplicationHelper().getModuleHelper(getModuleName());
		FormHelper formHelper = moduleHelper.getFormHelper(getApplicationBeanName());

		// Create Form
		Form newForm = formHelper.createForm();
		newForm.setParent(this);

		Long appBeanId = getApplicationBeanId();
		if (appBeanId == null) {
			ApplicationBean appBean = formHelper.createApplicationBean();
			((FormModel) newForm.getModel()).setApplicationBean(appBean);
		} else {

			ApplicationRequest req = ARUtils.createFindRequest(getModuleName(), getApplicationBeanName(), appBeanId);

			ARBUtils.getARB().executeRequest(req, new ARBAsyncCallback() {

				@Override
				public void onSuccess(Serializable result) {
					((FormModel) getForm().getModel()).setApplicationBean((ApplicationBean) result);
				}
			});
		}

		return newForm;
	}

	public void setApplicationBeanId(Long pkId) {
		setValue(APPLICATION_BEAN_ID, pkId);
	}

	public Long getApplicationBeanId() {
		return (Long) getValue(APPLICATION_BEAN_ID);
	}

	public class GWTExtFormBoardView implements FormBoardView {

		public Object getViewObject() {
			Panel formPanel = (Panel) getForm().getView().getViewObject();

			Long applicationBeanId = getApplicationBeanId();
			if (applicationBeanId == null) {
				formPanel.setTitle("New " + getApplicationBeanName());
			} else {
				Row row = RowCacheImpl.get().getRow(getModuleName(), getApplicationBeanName(), getDataBoard().getViewName(), applicationBeanId);
				formPanel.setTitle(getApplicationBeanName() + " ( " + row.getDisplayName() + " )");
			}
			return formPanel;
		}

	}

	@Override
	public ActionBase getActionBase() {
		return (ActionBase) getParent();
	}

}
