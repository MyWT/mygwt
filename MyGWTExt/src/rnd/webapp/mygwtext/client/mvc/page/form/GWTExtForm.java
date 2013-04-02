package rnd.webapp.mygwtext.client.mvc.page.form;

import java.util.List;
import java.util.Set;

import rnd.bean.ApplicationBean;
import rnd.bean.ValueChangeEvent;
import rnd.bean.ValueChangeListenerAdapter;
import rnd.bean._Bean;
import rnd.bean._BoundBean;
import rnd.mywt.client.expression.BindingManager;
import rnd.mywt.client.mvc.field.Field;
import rnd.mywt.client.mvc.page.board.DataBoard.DataBoardModel;
import rnd.mywt.client.mvc.page.board.FormBoard;
import rnd.mywt.client.mvc.page.form.Form;
import rnd.webapp.mygwtext.client.mvc.page.GWTExtPage;

import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.form.FormPanel;

public class GWTExtForm extends GWTExtPage implements Form {

	public GWTExtForm() {
		setModel(new GWTExtFormModel());
	}

	protected Panel createPanel() {
		FormPanel formPanel = new FormPanel();
		formPanel.setPaddings(15);
		return formPanel;
	}

	public void addField(Field field) {
		addChild(field);
	}

	public void removeField(Field field) {
		removeChild(field);
	}

	public Field getField(int index) {
		return (Field) getChild(index);
	}

	public List getFields() {
		return getChildren();
	}

	public class GWTExtFormModel implements FormModel {

		public GWTExtFormModel() {
			addValueChangeListener(APPLICATION_BEAN, new ValueChangeListenerAdapter() {

				public void valueChanged(ValueChangeEvent vce) {
					// Logger.startMethod("FM", "valueChanged");
					// Unbind Old App _Bean
					ApplicationBean oldAppBean = (ApplicationBean) vce.getOldValue();

					if (oldAppBean != null) {
						BindingManager.unbindForm(GWTExtForm.this, (_BoundBean) oldAppBean);
					}

					// Bind new _Bean
					ApplicationBean newAppBean = (ApplicationBean) vce.getNewValue();

					if (newAppBean != null) {

						// Copy Context
						_Bean contextBean = ((DataBoardModel) (((FormBoard) getParent()).getDataBoard().getModel())).getContextBean();
						Set<String> propertyNames = contextBean.getPropertyNames();

						for (String prpName : propertyNames) {
							newAppBean.setValue(prpName, contextBean.getValue(prpName));
						}

						Long appBeanId = newAppBean.getId();
						// Logger.log("appBeanId", appBeanId);

						// Init Form
						if (appBeanId != null) {
							BindingManager.initForm(GWTExtForm.this, (_BoundBean) newAppBean);
						}
						// Bind Form
						BindingManager.bindForm(GWTExtForm.this, (_BoundBean) newAppBean);
					}
					// Logger.endMethod("FM", "valueChanged");
				}

			});
		}

		public ApplicationBean getApplicationBean() {
			return (ApplicationBean) getValue(APPLICATION_BEAN);
		}

		public void setApplicationBean(ApplicationBean appBean) {
			setValue(APPLICATION_BEAN, appBean);
		}

	}
}