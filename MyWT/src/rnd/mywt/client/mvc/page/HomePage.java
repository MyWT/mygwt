package rnd.mywt.client.mvc.page;

import rnd.mywt.client.application.ApplicationHelper;
import rnd.mywt.client.mvc.page.board.ActionBoard;

public interface HomePage extends Page {

	void initializeApplication();

	void initializeFormAction(ApplicationHelper applicationHelper, boolean reload);

	ActionBoard getActionBoard();

}
