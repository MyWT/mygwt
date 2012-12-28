package rnd.webapp.mygwtext.client.mvc;

import rnd.mywt.client.mvc.MVCHandler;
import rnd.mywt.client.mvc.field.Table;
import rnd.mywt.client.mvc.field.data.ReferenceField;
import rnd.mywt.client.mvc.field.data.text.Label;
import rnd.mywt.client.mvc.field.data.text.TextArea;
import rnd.mywt.client.mvc.field.data.text.TextField;
import rnd.mywt.client.mvc.page.Page;
import rnd.mywt.client.mvc.page.board.ActionBar;
import rnd.mywt.client.mvc.page.board.ActionBase;
import rnd.mywt.client.mvc.page.board.ActionBoard;
import rnd.mywt.client.mvc.page.board.DataBoard;
import rnd.mywt.client.mvc.page.board.FormBoard;
import rnd.mywt.client.mvc.page.form.Form;
import rnd.webapp.mygwtext.client.mvc.field.GWTExtTable;
import rnd.webapp.mygwtext.client.mvc.field.data.GWTExtReferenceField;
import rnd.webapp.mygwtext.client.mvc.field.data.text.GWTExtTextArea;
import rnd.webapp.mygwtext.client.mvc.field.data.text.GWTExtTextField;
import rnd.webapp.mygwtext.client.mvc.page.GWTExtHomePage;
import rnd.webapp.mygwtext.client.mvc.page.GWTExtPage;
import rnd.webapp.mygwtext.client.mvc.page.board.GWTExtActionBar;
import rnd.webapp.mygwtext.client.mvc.page.board.GWTExtActionBase;
import rnd.webapp.mygwtext.client.mvc.page.board.GWTExtActionBoard;
import rnd.webapp.mygwtext.client.mvc.page.board.GWTExtDataBoard;
import rnd.webapp.mygwtext.client.mvc.page.board.GWTExtFormBoard;
import rnd.webapp.mygwtext.client.mvc.page.form.GWTExtForm;

public class GWTExtMVCHandler implements MVCHandler {

	// Form
	public Form createForm() {
		return new GWTExtForm();
	}

	// Label
	public Label createLabel(String text) {
		throw new UnsupportedOperationException("createLabel");
		// return new GWTExtLabel(text);
	}

	// Page
	public Page createPage() {
		return new GWTExtPage();
	}

	// Text Field
	public TextField createTextField(String label) {
		return new GWTExtTextField(label);
	}
	
	public TextArea createTextArea(String label) {
		return new GWTExtTextArea(label);
	}

	// Reference Field
	public ReferenceField createReferenceField(String label, String moduleName, String applicationBeanName, String viewName) {
		return new GWTExtReferenceField(label, moduleName, applicationBeanName, viewName);
	}

	// Table
	public Table createTable(int tableType) {
		return new GWTExtTable(tableType);
	}

	// Home Page
	public Page createHomePage() {
		return new GWTExtHomePage();
	}

	public Page createHomePage(ActionBoard actionBoard) {
		return new GWTExtHomePage(actionBoard);
	}

	// Action Board
	public ActionBoard createActionBoard() {
		return new GWTExtActionBoard();
	}

	public ActionBoard createActionBoard(ActionBar actionBar, ActionBase actionBase) {
		return new GWTExtActionBoard(actionBar, actionBase);
	}

	// Action Bar
	public ActionBar createActionBar() {
		return new GWTExtActionBar();
	}

	// Action Base
	public ActionBase createActionBase() {
		return new GWTExtActionBase();
	}

	// Form Board
	public FormBoard createFormBoard(String moduleName, String applicationBeanName) {
		return new GWTExtFormBoard(moduleName, applicationBeanName);
	}

	// Data Board
	public DataBoard createDataBoard(String moduleName, String applicationBeanName, String viewName) {
		return new GWTExtDataBoard(moduleName, applicationBeanName, viewName);
	}

}
