package model;

import java.awt.Point;
import java.util.Random;

/**
 * This strategy selects the first available move at random. It is easy to beat
 * 
 * @throws IGotNowhereToGoException
 *             whenever asked for a move that is impossible to deliver
 * 
 * @author mercer
 */
public class StopperAI implements TicTacToeStrategy {

	private static Random generator;
	private TicTacToeGame theGame;
	private char[][] board;
	private int ROW;
	private int COL;
	private int size = 3;

	public StopperAI() {
		generator = new Random();
	}

	@Override
	public Point desiredMove(TicTacToeGame game) {
		theGame = game;
		board = theGame.getTicTacToeBoard();

		if (checkRowsForDoubleX()) {
			return new Point(ROW, COL);
		} else if (checkColsForDoubleX()) {
			return new Point(ROW, COL);
		} else if (checkDiagonalForDoubleX()) {
			return new Point(ROW, COL);
		} else {
			// random selection
			boolean set = false;
			while (!set) {
				if (theGame.maxMovesRemaining() == 0)
					throw new IGotNowhereToGoException(
							" -- Hey there programmer, the board is filled");

				// Otherwise, try to randomly find an open spot
				// ToDo: write a strategy that blocks winning move
				// otherwise is random

				int row = generator.nextInt(3);
				int col = generator.nextInt(theGame.size());
				if (theGame.available(row, col)) {
					set = true;
					return new Point(row, col);
				}
			}
			return null;
		}

	}// end Point

	private boolean checkDiagonalForDoubleX() {
		if (board[1][1] == 'X') {
			if (board[0][0] == 'X' && theGame.available(2, 2)) {
				ROW = 2;
				COL = 2;
				return true;
			}
			if (board[2][2] == 'X' && theGame.available(0, 0)) {
				ROW = 0;
				COL = 0;
				return true;
			}
			if (board[0][2] == 'X' && theGame.available(2, 0)) {
				ROW = 2;
				COL = 0;
				return true;
			}
			if (board[2][0] == 'X' && theGame.available(0, 2)) {
				ROW = 0;
				COL = 2;
				return true;
			}
		}
		return false;
	}

	private boolean checkColsForDoubleX() {
		for (int c = 0; c < size; c++) {
			if (board[0][c] == 'X' && board[1][c] == 'X'
					&& theGame.available(2, c)) {
				ROW = 2;
				COL = c;
				return true;
			}
			if (board[1][c] == 'X' && board[2][c] == 'X'
					&& theGame.available(0, c)) {
				ROW = 0;
				COL = c;
				return true;
			}
		}
		return false;
	}

	private boolean checkRowsForDoubleX() {
		for (int r = 0; r < size; r++) {
			if (board[r][0] == 'X' && board[r][1] == 'X'
					&& theGame.available(r, 2)) {
				ROW = r;
				COL = 2;
				return true;
			}
			if (board[r][1] == 'X' && board[r][2] == 'X'
					&& theGame.available(r, 0)) {
				ROW = r;
				COL = 0;
				return true;
			}
		}
		return false;
	}

}