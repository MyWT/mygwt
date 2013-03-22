package rnd.mywt.client.mvc.page;

import rnd.mywt.client.application.ApplicationHelper;

public interface HomePage extends Page {

	void initializeApplication();

	void initializeFormAction(ApplicationHelper applicationHelper, boolean reload);

}
