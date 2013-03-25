package rnd.webapp.mygwtext.client.mvc.page;

import java.util.List;

import rnd.mywt.client.mvc.AbstractMVCBean;
import rnd.mywt.client.mvc.MVCBean;
import rnd.mywt.client.mvc.page.Page;

import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.Panel;

public class GWTExtPage extends AbstractMVCBean implements Page {

	protected Panel panel;
	
	public GWTExtPage() {
		setView(new GWTExtPageView());
	}

	public MVCBean addChild(MVCBean child) {
		addElement(CHILD, child);
		child.setParent(this);
		getPageView().addChild(child.getView());
		return child;
	}

	public MVCBean removeChild(MVCBean child) {
		removeElement(CHILD, child);
		child.setParent(null);
		getPageView().removeChild(child.getView());
		return child;
	}

	public MVCBean getChild(int index) {
		return (MVCBean) getElement(CHILD, index);
	}

	public List getChildren() {
		return getListValueReadOnly(CHILD);
	}

	protected Panel getPanel() {
		if (this.panel == null) {
			this.panel = createPanel();
		}
		return this.panel;
	}

	protected Panel createPanel() {
		return new Panel();
	}

	private PageView getPageView() {
		return ((PageView) getView());
	}

	// View

	public class GWTExtPageView implements PageView {

		public View addChild(View childView) {
			// Logger.startMethod("GWTExtPageView", "addChild");
			Widget w = (Widget) childView.getViewObject();
			getPanel().add(w);
			getPanel().doLayout();
			// Logger.endMethod("GWTExtPageView", "addChild");
			return childView;
		}

		public View removeChild(View childView) {
			getPanel().remove((Widget) childView.getViewObject());
			// getPanel().doLayout();
			return childView;
		}

		public Object getViewObject() {
			return getPanel();
		}

	}

}