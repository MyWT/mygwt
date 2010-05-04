package rnd.mywt.client.mvc.page.board;

import rnd.mywt.client.mvc.page.Page;

public interface ActionBoard extends Page {

	ActionBase getActionBase();

	ActionBar getActionBar();

	public interface ActionBoardView extends PageView {
	}

}
