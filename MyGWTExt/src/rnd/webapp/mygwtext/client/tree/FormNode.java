package rnd.webapp.mygwtext.client.tree;

import com.gwtext.client.widgets.tree.TreeNode;

public class FormNode extends TreeNode {

	public FormNode(String appBeanName, String formName, String viewName) {
		super(formName);
		NodeUtils.setTypeForm(this);
		NodeUtils.setAppBeanName(this, appBeanName);
		NodeUtils.setFormName(this, formName);
		NodeUtils.setViewName(this, viewName);
	}
}
