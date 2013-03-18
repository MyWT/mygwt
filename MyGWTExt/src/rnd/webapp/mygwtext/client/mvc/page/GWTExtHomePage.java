package rnd.webapp.mygwtext.client.mvc.page;

import static rnd.webapp.mygwtext.client.tree.NodeUtils.getFormName;
import static rnd.webapp.mygwtext.client.tree.NodeUtils.getModuleName;
import static rnd.webapp.mygwtext.client.tree.NodeUtils.getViewName;

import java.util.Collection;

import rnd.mywt.client.MyWTHelper;
import rnd.mywt.client.application.ApplicationHelper;
import rnd.mywt.client.application.DefaultApplicationHelper;
import rnd.mywt.client.application.FormHelper;
import rnd.mywt.client.application.ModuleHelper;
import rnd.mywt.client.mvc.MVCHandlerFactory;
import rnd.mywt.client.mvc.page.Page;
import rnd.mywt.client.mvc.page.board.ActionBoard;
import rnd.mywt.client.mvc.page.board.DataBoard;
import rnd.mywt.client.mvc.page.board.Board.BoardType;
import rnd.webapp.mygwtext.client.tree.FormNode;
import rnd.webapp.mygwtext.client.tree.ModuleNode;
import rnd.webapp.mygwtext.client.tree.NodeUtils;
import rnd.webapp.mygwtext.client.tree.RootNode;

import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.MessageBoxConfig;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.Viewport;
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

public class GWTExtHomePage extends GWTExtPage implements Page {

	private ActionBoard actionBoard;

	public GWTExtHomePage() {
		this(MVCHandlerFactory.getMVCHandler().createActionBoard());
	}

	public GWTExtHomePage(ActionBoard actionBoard) {
		this.actionBoard = actionBoard;
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

	private void addFormActionPanel(Panel borderPanel) {
		final Panel formActionPanel = new Panel();
		formActionPanel.setTitle("Form");
		formActionPanel.setCollapsible(true);
		formActionPanel.setWidth("25%");
		BorderLayoutData westData = new BorderLayoutData(RegionPosition.WEST);
		westData.setSplit(true);
		borderPanel.add(formActionPanel, westData);

		TreePanel treePanel = new TreePanel("");
		treePanel.setRootVisible(false);
		treePanel.setBorder(false);

		RootNode root = new RootNode();

		ApplicationHelper applicationHelper = MyWTHelper.getApplicationHelper();
		
		if(applicationHelper ==null ){
			applicationHelper = new DefaultApplicationHelper(MyWTHelper.getApplicationName());
		}
		
		Collection<ModuleHelper> moduleHelpers = applicationHelper.getModuleHelpers();
		// System.out.println("module:" + moduleHelpers.size());

		for (ModuleHelper moduleHelper : moduleHelpers) {
			Collection<FormHelper> formHelpers = moduleHelper.getFormHelpers();
			// System.out.println("\tform:" + formHelpers.size());

			ModuleNode module = new ModuleNode(moduleHelper.getModuleName());
			root.addModuleNode(module);

			for (FormHelper formHelper : formHelpers) {
				FormNode form = new FormNode(formHelper.getFormName(), formHelper.getViewName());
				module.addFormNode(form);
			}
		}

		treePanel.setRootNode(root);

		treePanel.addListener(new TreePanelListenerAdapter() {

			@Override
			public void onDblClick(TreeNode node, EventObject e) {
//				Logger.startMethod("", "onDblClick");
//				Logger.log("node", node);
				
				if (NodeUtils.isFormNode(node)) {
					try {

						String moduleName = getModuleName(node.getParentNode());
						String formName = getFormName(node);
						String viewName = getViewName(node);

						DataBoard dataBoard = (DataBoard) actionBoard.getActionBase().getBoard(moduleName, formName, viewName, BoardType.DATA_BOARD);

						if (dataBoard == null) {
							dataBoard = MVCHandlerFactory.getMVCHandler().createDataBoard(moduleName, formName, viewName);
							actionBoard.getActionBase().addBoard(dataBoard);
						}

						actionBoard.getActionBase().setCurrentBoard(dataBoard);

					}
					catch (RuntimeException re) {
						re.printStackTrace();
						throw re;
					}
				}
//				Logger.endMethod("", "onDblClick");
			}
		});

		formActionPanel.add(treePanel);
	}
}
