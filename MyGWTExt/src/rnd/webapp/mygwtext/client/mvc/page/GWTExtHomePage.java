package rnd.webapp.mygwtext.client.mvc.page;

import java.util.Collection;

import rnd.mywt.client.MyWTHelper;
import rnd.mywt.client.application.ApplicationHelper;
import rnd.mywt.client.application.DefaultApplicationHelper;
import rnd.mywt.client.application.FormHelper;
import rnd.mywt.client.application.ModuleHelper;
import rnd.mywt.client.mvc.page.HomePage;
import rnd.mywt.client.mvc.page.board.ActionBoard;
import rnd.mywt.client.mvc.page.board.Board.BoardType;
import rnd.mywt.client.mvc.page.board.DataBoard;
import rnd.webapp.mygwtext.client.tree.FormNode;
import rnd.webapp.mygwtext.client.tree.ModuleNode;
import rnd.webapp.mygwtext.client.tree.NodeUtils;
import rnd.webapp.mygwtext.client.tree.RootNode;

import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.data.Node;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.MessageBoxConfig;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.Viewport;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.BorderLayoutData;
import com.gwtext.client.widgets.layout.FitLayout;
import com.gwtext.client.widgets.menu.BaseItem;
import com.gwtext.client.widgets.menu.Item;
import com.gwtext.client.widgets.menu.Menu;
import com.gwtext.client.widgets.menu.event.BaseItemListenerAdapter;
import com.gwtext.client.widgets.tree.TreeNode;
import com.gwtext.client.widgets.tree.TreePanel;
import com.gwtext.client.widgets.tree.event.TreePanelListenerAdapter;

public class GWTExtHomePage extends GWTExtPage implements HomePage {

	private ActionBoard actionBoard;

	public GWTExtHomePage() {
		this(MyWTHelper.getMVCFactory().createActionBoard());
	}

	public GWTExtHomePage(ActionBoard actionBoard) {
		this.actionBoard = actionBoard;
	}

	public ActionBoard getActionBoard() {
		return actionBoard;
	}

	@Override
	protected Panel createPanel() {

		Panel borderPanel = new Panel();
		borderPanel.setLayout(new BorderLayout());

		// top menu
		addMenuPanel(borderPanel);

		// west panel
		addFormActionPanel(borderPanel);

		// center panel
		addActionBoard(borderPanel);

		// south panel
		addStatusPanel(borderPanel);

		Panel mainPanel = new Panel();
		mainPanel.setLayout(new FitLayout());
		mainPanel.add(borderPanel);

		new Viewport(mainPanel);

		return mainPanel;
	}

	private void addMenuPanel(Panel borderPanel) {
		// MenuBar
		Toolbar menuBar = new Toolbar();

		// System Menu
		ToolbarButton systemButton = new ToolbarButton("System");
		Menu systemMenu = new Menu();
		Item login = new Item("Login");
		systemMenu.addItem(login);
		systemButton.setMenu(systemMenu);
		menuBar.addButton(systemButton);

		// Help Menu
		ToolbarButton helpButton = new ToolbarButton("Help");
		Menu helpMenu = new Menu();
		Item about = new Item("About", new BaseItemListenerAdapter() {
			@Override
			public void onClick(BaseItem item, EventObject e) {
				MessageBoxConfig config = new MessageBoxConfig();
				config.setClosable(false);
				config.setModal(true);
				config.setMsg("Testing");
				MessageBox.show(config);
			}
		});
		helpMenu.addItem(about);
		helpButton.setMenu(helpMenu);
		menuBar.addButton(helpButton);

		// Help Menu
		ToolbarButton reloadButton = new ToolbarButton("Reload");

		reloadButton.addListener(new ButtonListenerAdapter() {
			@Override
			public void onClick(Button button, EventObject e) {
				initializeApplication();
			}
		});
		menuBar.addButton(reloadButton);

		borderPanel.setTopToolbar(menuBar);

	}

	private void addActionBoard(Panel borderPanel) {
		borderPanel.add((Panel) this.actionBoard.getView().getViewObject(), new BorderLayoutData(RegionPosition.CENTER));
	}

	private void addStatusPanel(Panel borderPanel) {
		Panel southPanel = new Panel();
		southPanel.setTitle("Status");
		southPanel.setCollapsible(true);
		southPanel.collapse();
		BorderLayoutData southData = new BorderLayoutData(RegionPosition.SOUTH);
		southPanel.setHeight("25");
		southData.setSplit(true);
		borderPanel.add(southPanel, southData);
	}

	static int i = 0;

	private TreePanel treePanel = new TreePanel("");;
	RootNode root = new RootNode();

	private void addFormActionPanel(Panel borderPanel) {
		final Panel formActionPanel = new Panel();
		formActionPanel.setTitle("Form");
		formActionPanel.setCollapsible(true);
		formActionPanel.setWidth("25%");
		BorderLayoutData westData = new BorderLayoutData(RegionPosition.WEST);
		westData.setSplit(true);
		borderPanel.add(formActionPanel, westData);

		treePanel.setRootVisible(false);
		treePanel.setBorder(false);

		initializeApplication();

		treePanel.setRootNode(root);
		treePanel.addListener(new TreePanelListenerAdapter() {

			@Override
			public void onDblClick(TreeNode node, EventObject e) {
				// Logger.startMethod("", "onDblClick");
				// Logger.log("node", node);

				if (NodeUtils.isFormNode(node)) {
					try {

						String moduleName = NodeUtils.getModuleName(node.getParentNode());
						String appBeanName = NodeUtils.getAppBeanName(node);
						String viewName = NodeUtils.getViewName(node);

						DataBoard dataBoard = (DataBoard) actionBoard.getActionBase().getBoard(moduleName, appBeanName, viewName, BoardType.DATA_BOARD);

						if (dataBoard == null) {

							ModuleHelper moduleHelper = MyWTHelper.getApplicationHelper().getModuleHelper(moduleName);
							if (moduleHelper == null) {
								moduleHelper = MyWTHelper.getDefaultApplicationHelper().getModuleHelper(moduleName);
							}
							if (moduleHelper != null) {
								FormHelper formHelper = moduleHelper.getFormHelper(appBeanName);
								if (formHelper != null) {
									dataBoard = formHelper.createDataBoard();
								}
							}

							if (dataBoard == null) {
								dataBoard = MyWTHelper.getMVCFactory().createDataBoard(moduleName, appBeanName, viewName);
							}
							actionBoard.getActionBase().addBoard(dataBoard);
						}
						actionBoard.getActionBase().setCurrentBoard(dataBoard);

					} catch (RuntimeException re) {
						re.printStackTrace();
						throw re;
					}
				}
				// Logger.endMethod("", "onDblClick");
			}
		});

		formActionPanel.add(treePanel);
	}

	public void initializeApplication() {
		DefaultApplicationHelper defaultApplicationHelper = new DefaultApplicationHelper(MyWTHelper.getApplicationName());
		MyWTHelper.setDefaultApplicationHelper(defaultApplicationHelper);
	}

	@Override
	public void initializeFormAction(ApplicationHelper applicationHelper, boolean reload) {

		if (reload) {
			for (Node firstChild = root.getFirstChild(); firstChild != null; firstChild = root.getFirstChild()) {
				root.removeChild(firstChild);
			}
		}

		Collection<ModuleHelper> moduleHelpers = applicationHelper.getModuleHelpers();
		// System.out.println("module:" + moduleHelpers.size());

		for (ModuleHelper moduleHelper : moduleHelpers) {
			Collection<FormHelper> formHelpers = moduleHelper.getFormHelpers();
			// System.out.println("\tform:" + formHelpers.size());

			ModuleNode module = new ModuleNode(moduleHelper.getModuleName());
			root.addModuleNode(module);

			for (FormHelper formHelper : formHelpers) {
				FormNode form = new FormNode(formHelper.getAppBeanName(), formHelper.getFormName(), formHelper.getViewName());
				module.addFormNode(form);
			}
		}
		treePanel.expandAll();
	}
}
