package rnd.webapp.mygwtext.client.tree;

import com.gwtext.client.widgets.tree.TreeNode;

public class FormNode extends TreeNode {

	public FormNode(String formName, String viewName) {
		super(formName);
		NodeUtils.setTypeForm(this);
		NodeUtils.setFormName(this, formName);
		NodeUtils.setViewName(this, viewName);
	}
}
