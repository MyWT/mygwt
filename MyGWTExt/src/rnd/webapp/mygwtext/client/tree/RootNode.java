package rnd.webapp.mygwtext.client.tree;

import com.gwtext.client.widgets.tree.TreeNode;

public class RootNode extends TreeNode {
	public RootNode() {
		super("root");
	}

	public void addModuleNode(ModuleNode module) {
		appendChild(module);
	}

}
