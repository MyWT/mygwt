package rnd.webapp.mygwtext.client.mvc.page.board;

import java.io.Serializable;

import rnd.mywt.client.MWTHelper;
import rnd.mywt.client.application.FormHelper;
import rnd.mywt.client.application.ModuleHelper;
import rnd.mywt.client.bean.ApplicationBean;
import rnd.mywt.client.data.RowCacheImpl;
import rnd.mywt.client.mvc.page.board.FormBoard;
import rnd.mywt.client.mvc.page.board.FormBoard.FormBoardView;
import rnd.mywt.client.mvc.page.form.Form.FormModel;
import rnd.mywt.client.rpc.ApplicationRequest;
import rnd.webapp.mygwt.client.ARBAsyncCallback;

import com.gwtext.client.widgets.Panel;

public class GWTExtFormBoard extends GWTExtBoard implements FormBoard {

	private Form form;

	public GWTExtFormBoard(String moduleName, String appBeanName) {
		super(moduleName, appBeanName, null);
		setView(new GWTExtFormBoardView());
	}

	public void setViewName(String viewName) {
		setValue(VIEW_NAME, viewName);
	}

	public Form getForm() {
		if (this.form == null) {
			this.form = createForm();
		}
		return this.form;
	}

	public BoardType getBoardType() {
		return BoardType.FORM_BOARD;
	}

	private Form createForm() {

		ModuleHelper moduleHelper = MWTHelper.getApplicationHelper().getModuleHelper(getModuleName());
		FormHelper formHelper = moduleHelper.getFormHelper(getApplicationBeanName());

		// Create Form
		Form newForm = formHelper.createForm();
		newForm.setParent(this);

		Long appBeanId = getApplicationBeanId();
		if (appBeanId == null) {
			ApplicationBean appBean = formHelper.createApplicationBean();
			((FormModel) newForm.getModel()).setApplicationBean(appBean);
		} else {

			ApplicationRequest req = ARCreator.createFindRequest(getModuleName(), getApplicationBeanName(), appBeanId);

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
				Row row = RowCacheImpl.get().getRow(getModuleName(), getApplicationBeanName(), getViewName(), applicationBeanId);
				formPanel.setTitle(getApplicationBeanName() + " ( " + row.getDisplayName() + " )");
			}
			return formPanel;
		}

	}

}
