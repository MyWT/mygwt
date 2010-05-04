package rnd.mywt.client.mvc.page.board;

import rnd.mywt.client.mvc.page.Page;
import rnd.mywt.client.mvc.page.board.Board.BoardType;

public interface ActionBase extends Page {

	// Action Board

	// ActionBoard getActionBoard();

	// void setActionBoard(ActionBoard actionBoard);

	// Board

	BoardType getCurrentBoardType();

	void setCurrentBoard(Board board);

	Board getCurrentBoard();

	void removeCurrentBoard();

	String BOARD = "board";

	Board addBoard(Board board);

	Board removeBoard(int index);

	void removeBoard(Board board);

	Board getBoard(int index);

	Board getBoard(String moduleName, String appBeanName, String viewName, BoardType boardType);

	// Model
	// interface ActionBaseModel extends PageModel {
	// }

	// View
	interface ActionBaseView extends PageView {
	}

}
