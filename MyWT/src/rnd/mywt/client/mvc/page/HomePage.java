package rnd.mywt.client.mvc.page;

import rnd.mywt.client.mvc.page.board.ActionBoard;

public interface HomePage extends Page {

	void initializeFormAction();

	ActionBoard getActionBoard();

}
