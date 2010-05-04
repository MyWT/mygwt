package rnd.webapp.mygwtext.client.tree;

import com.gwtext.client.widgets.tree.TreeNode;

public class ModuleNode extends TreeNode {

	public ModuleNode(String moduleName) {
		super(moduleName);
		NodeUtils.setTypeModule(this);
		NodeUtils.setModuleName(this, moduleName);
	}

	public void addFormNode(FormNode formNode) {
		appendChild(formNode);
	}

}
