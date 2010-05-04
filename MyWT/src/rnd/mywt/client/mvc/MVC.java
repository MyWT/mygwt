package rnd.mywt.client.mvc;

public interface MVC {

	void setParent(MVC parent);

	MVC getParent();

	String MODEL = "model";

	Model getModel();

	void setModel(Model m);

	String VIEW = "view";

	View getView();

	void setView(View v);

	public interface Model {
	}

	public interface View {
		Object getViewObject();
	}
}
