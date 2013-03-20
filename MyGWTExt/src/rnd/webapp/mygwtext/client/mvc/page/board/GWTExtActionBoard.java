package rnd.webapp.mygwtext.client.mvc.page.board;

import rnd.mywt.client.MyWTHelper;
import rnd.mywt.client.mvc.page.board.ActionBar;
import rnd.mywt.client.mvc.page.board.ActionBase;
import rnd.mywt.client.mvc.page.board.ActionBoard;
import rnd.webapp.mygwtext.client.mvc.page.GWTExtPage;

import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.TabPanel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.BorderLayoutData;

public class GWTExtActionBoard extends GWTExtPage implements ActionBoard {

	private ActionBar actionBar;
	private ActionBase actionBase;

	public GWTExtActionBoard() {
		this(MyWTHelper.getMVCHandler().createActionBar(), MyWTHelper.getMVCHandler().createActionBase());
	}

	public GWTExtActionBoard(ActionBar actionBar, ActionBase actionBase) {
		this.actionBar = actionBar;
		this.actionBar.setParent(this);

		this.actionBase = actionBase;
		this.actionBase.setParent(this);

		setView(new GWTExtActionBoardView());
	}

	public ActionBar getActionBar() {
		return this.actionBar;
	}

	public ActionBase getActionBase() {
		return this.actionBase;
	}

	@Override
	protected Panel createPanel() {
		Panel actionBoard = super.createPanel();
		actionBoard.setBorder(false);
		actionBoard.setLayout(new BorderLayout());
		return actionBoard;
	}

	public class GWTExtActionBoardView extends GWTExtPageView implements ActionBoardView {
		public GWTExtActionBoardView() {

			Panel actionBoardPanel = (Panel) super.getViewObject();

			// Add Action Bar
			actionBoardPanel.setTopToolbar((Toolbar) getActionBar().getView().getViewObject());

			// Add Action Base
			actionBoardPanel.add((TabPanel) getActionBase().getView().getViewObject(), new BorderLayoutData(RegionPosition.CENTER));
		}
	}

}
