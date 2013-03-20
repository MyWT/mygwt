package rnd.mywt.client.mvc;

import rnd.mywt.client.mvc.field.Table;
import rnd.mywt.client.mvc.field.data.ReferenceField;
import rnd.mywt.client.mvc.field.data.text.Label;
import rnd.mywt.client.mvc.field.data.text.TextArea;
import rnd.mywt.client.mvc.field.data.text.TextField;
import rnd.mywt.client.mvc.page.HomePage;
import rnd.mywt.client.mvc.page.Page;
import rnd.mywt.client.mvc.page.board.ActionBar;
import rnd.mywt.client.mvc.page.board.ActionBase;
import rnd.mywt.client.mvc.page.board.ActionBoard;
import rnd.mywt.client.mvc.page.board.DataBoard;
import rnd.mywt.client.mvc.page.board.FormBoard;
import rnd.mywt.client.mvc.page.form.Form;

public interface MVCHandler {

	// Page
	Page createPage();

	// Form
	Form createForm();

	// Label
	Label createLabel(String text);

	// Text Field
	TextField createTextField(String label);

	// Text Area
	TextArea createTextArea(String label);

	// Reference Field
	ReferenceField createReferenceField(String label, String moduleName, String applicationBeanName, String viewName);

	// Table
	Table createTable(int tableType);

	// Home Page
	HomePage createHomePage();

	HomePage createHomePage(ActionBoard actionBoard);

	// Action Board

	ActionBoard createActionBoard();

	ActionBoard createActionBoard(ActionBar actionBar, ActionBase actionBase);

	// Action Bar
	ActionBar createActionBar();

	// Action Base
	ActionBase createActionBase();

	// Form Board
	FormBoard createFormBoard(String moduleName, String applicationBeanName);

	// Data Board
	DataBoard createDataBoard(String moduleName, String applicationBeanName, String viewName);

}
