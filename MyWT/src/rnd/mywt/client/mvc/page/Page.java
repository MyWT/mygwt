package rnd.mywt.client.mvc.page;

import java.util.List;

import rnd.mywt.client.mvc.MVCBean;

public interface Page extends MVCBean {

	String CHILD = "child";

	MVCBean addChild(MVCBean child);

	MVCBean removeChild(MVCBean child);

	MVCBean getChild(int index);

	List getChildren();

	// public interface PageModel extends Model {
	// }

	public interface PageView extends View {

		View addChild(View childView);

		View removeChild(View childView);

	}
}