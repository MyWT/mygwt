package rnd.webapp.mygwtext.client.mvc.page.board;

import java.util.List;

import rnd.mywt.client.mvc.page.board.ActionBase;
import rnd.mywt.client.mvc.page.board.ActionBoard;
import rnd.mywt.client.mvc.page.board.Board;
import rnd.mywt.client.mvc.page.board.DataBoard;
import rnd.mywt.client.mvc.page.board.Board.BoardType;
import rnd.webapp.mygwtext.client.mvc.page.GWTExtPage;

import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.TabPanel;
import com.gwtext.client.widgets.event.TabPanelListenerAdapter;

public class GWTExtActionBase extends GWTExtPage implements ActionBase {

	public GWTExtActionBase() {
		setView(new GWTExtActionBaseView());
	}

	private Board currentBoard;

	@Override
	protected Panel createPanel() {

		TabPanel actionBase = new TabPanel();
		actionBase.setResizeTabs(true);
		actionBase.setMinTabWidth(100);
		actionBase.setTabWidth(150);
		actionBase.setBorder(false);
		actionBase.setEnableTabScroll(true);

		actionBase.addListener(new TabPanelListenerAdapter() {

			@Override
			public void onTabChange(TabPanel source, Panel tab) {

				Component[] components = source.getComponents();

				for (int i = 0; i < components.length; i++) {
					if (components[i].getId().equals(tab.getId())) {
						// Logger.log("components[i]", i);
						setCurrentBoard(getBoard(i));
						break;
					}
				}

			}

		});

		return actionBase;
	}

	// public ActionBoard getActionBoard() {
	// return this.actionBoard;
	// }

	public ActionBoard getActionBoard() {
		return (ActionBoard) getParent();
	}

	// public void setActionBoard(ActionBoard actionBoard) {
	// this.actionBoard = actionBoard;
	// }

	public BoardType getCurrentBoardType() {
		return this.currentBoard == null ? null : this.currentBoard.getBoardType();
	}

	public void setCurrentBoard(Board board) {
		if (this.currentBoard == board) {
			return;
		}
		this.currentBoard = board;
		activateView(board.getView());
	}

	public Board getCurrentBoard() {
		return this.currentBoard;
	}

	// Board

	// static
	int nextBoardCount;

	public Board addBoard(Board board) {
		addElement(BOARD, board);
		addChild(board);
		return board;
	}

	public Board removeBoard(int index) {
		Board board = (Board) removeElement(BOARD, index);
		removeChild(board);
		return board;
	}

	public void removeCurrentBoard() {
		removeBoard(getCurrentBoard());
	}

	public void removeBoard(Board board) {
		removeElement(BOARD, board);
		removeChild(board);
	}

	public Board getBoard(int index) {
		return (Board) getElement(BOARD, index);
	}

	public Board getBoard(String moduleName, String appBeanName, String viewName, BoardType boardType) {
		List<Board> boards = getListValue(BOARD);
		switch (boardType) {
		case FORM_BOARD:
			for (Board board : boards) {
				if (board.getBoardType() == BoardType.FORM_BOARD && //
						board.getModuleName().equals(moduleName) && //
						board.getApplicationBeanName().equals(appBeanName)) {
					return board;
				}
			}
		case DATA_BOARD:
			for (Board board : boards) {
				if (board.getBoardType() == BoardType.DATA_BOARD && //
						board.getModuleName().equals(moduleName) && //
						board.getApplicationBeanName().equals(appBeanName) && //
						((DataBoard) board).getViewName().equals(viewName)) {
					return board;
				}
			}
		}
		return null;
	}

	public class GWTExtActionBaseView extends GWTExtPageView implements ActionBaseView {

		@Override
		public View addChild(View childView) {
			Panel boardPanel = (Panel) childView.getViewObject();
			boardPanel.setBorder(false);
			boardPanel.setAutoScroll(true);
			return super.addChild(childView);
		}

	}

	private void activateView(View view) {
		String id = ((Component) view.getViewObject()).getId();
		((TabPanel) getPanel()).activate(id);
	}

}
