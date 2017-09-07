package net.devstudy.basic.game.gomoku;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;


public class Gomoku {
	public static int SIZE = 15;
	public static int WIN_COUNT = 5;

	public static char NO_WINNER = 0;
	public static char EMPTY = ' ';
	public static char HUMAN = 'X';
	public static char COMPUTER = 'O';

	public static char[][] gameTable = new char[SIZE][SIZE];
	public static JLabel cells[][];

	public static int count = 0;
	public static int[][] winningCoordinates = new int[WIN_COUNT][2];

	public static int compX;
	public static int compY;
	public static int TurnPriority;

	public static void init() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				gameTable[i][j] = EMPTY;
			}
		}
	}

	public static void makeTurn(int i, int j, char figure) {
		gameTable[i][j] = figure;
		drawFigure(i, j);
	}

	public static void drawFigure(int i, int j) {
		cells[i][j].setText(String.valueOf(gameTable[i][j]));
	}

	public static boolean isCellFree(int i, int j) {
		return gameTable[i][j] == EMPTY;
	}

	public static void makeHumanTurn(int i, int j) {
		makeTurn(i, j, HUMAN);
	}

	public static boolean hasEmptyCells() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (gameTable[i][j] == EMPTY) {
					return true;
				}
			}
		}
		return false;
	}

	public static void clear() {
		count = 0;
	}

	public static void addWinningCoordinate(int row, int col) {
		winningCoordinates[count][0] = row;
		winningCoordinates[count][1] = col;
		count++;
	}

	public static void markWinningCombinationByRedColor() {
		for (int k = 0; k < WIN_COUNT; k++) {
			int row = winningCoordinates[k][0];
			int col = winningCoordinates[k][1];
			cells[row][col].setForeground(Color.RED);
			cells[row][col].setFont(new Font(Font.SERIF, Font.BOLD, 35));
		}
	}

	public static boolean CompTurnByRow(char figure, int searchLen) {
		// FIXME !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		for (int i = 0; i < SIZE; i++) {
			clear();
			for (int j = 0; j < SIZE; j++) {
				if (figure == gameTable[i][j]) {
					addWinningCoordinate(i, j);
				} else if (figure != gameTable[i][j]) {
					clear();
				}
				if (count == searchLen){
					if (j+1 < SIZE && gameTable[i][j+1]==EMPTY){
						makeTurn(i, j+1, COMPUTER);
						return true;
					} else if (j-searchLen >= 0 && gameTable[i][j-searchLen]==EMPTY) {
						makeTurn(i, j-searchLen, COMPUTER);
						return true;
					}
				}
			}
		}
		return false;
		// FIXME !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	}

	public static boolean CompTurnByCol(char figure, int searchLen) {
		for (int i = 0; i < SIZE; i++) {
			clear();
			for (int j = 0; j < SIZE; j++) {
				if (figure == gameTable[j][i]) {
					addWinningCoordinate(j, i);
				} else if (figure != gameTable[j][i]) {
					clear();
				}
				if (count == searchLen){
					if (j+1 < SIZE && gameTable[j+1][i]==EMPTY){
						makeTurn(j+1, i, COMPUTER);
						return true;
					} else if (j-searchLen >= 0 && gameTable[j - searchLen][i]==EMPTY) {
						makeTurn(j-searchLen, i, COMPUTER);
						return true;
					}
				}
			}
		}
		return false;
	}

	public static  boolean CompTurnByMainDiagonal(char figure, int searchLen) {
		int start_point = SIZE - WIN_COUNT;
		int x = start_point;
		int iterations = SIZE - x;
		int y = 0;
		for (int i = 0; i < (start_point) * 2 + 1; i++) {
			int j = x;
			int k = y;
			for (int n = 0; n < iterations; n++) {
				if (figure == gameTable[j][k]) {
					addWinningCoordinate(j, k);
				} else if (figure != gameTable[j][k]) {
					clear();
				}
				k++;
				j++;
				if (count == searchLen){
					if (j < SIZE && k<SIZE && gameTable[j][k]==EMPTY){
						makeTurn(j, k, COMPUTER);
						return true;
					} else if (j-searchLen-1 >= 0 && k-searchLen-1 >= 0 && gameTable[j - searchLen-1][k-searchLen-1]==EMPTY) {
						makeTurn(j-searchLen-1, k-searchLen-1, COMPUTER);
						return true;
					}
				}
			}
			if (x > 0) {
				x--;
				y = 0;
				iterations = SIZE - x;
			} else if (x == 0 && y == 0) {
				y++;
				iterations = SIZE - y;
			} else if (x == 0 && y > 0) {
				y++;
				iterations = SIZE - y;
			}
		}

		return false;
	}

	public static boolean CompTurnSecondaryDiagonal(char figure, int searchLen) {
		int start_point = SIZE - WIN_COUNT;
		int x = start_point;
		int iterations = SIZE - x;
		int y = SIZE - 1;
		for (int i = 0; i < (start_point) * 2 + 1; i++) {
			int j = x;
			int k = y;
			for (int n = 0; n < iterations; n++) {
				if (figure == gameTable[j][k]) {
					addWinningCoordinate(j, k);
				} else if (figure != gameTable[j][k]) {
					clear();
				}
				j++;
				k--;
				if (count == searchLen){
					if (j < SIZE && k < SIZE && gameTable[j][k]==EMPTY){
						makeTurn(j, k, COMPUTER);
						return true;
					} else if (j-searchLen-1 >= 0 && k+searchLen+1 >= 0 && gameTable[j - searchLen-1][k+searchLen+1]==EMPTY) {
						makeTurn(j-searchLen-1, k+searchLen+1, COMPUTER);
						return true;
					}
				}
			}
			if (x > 0) {
				x--;
				y = SIZE - 1;
				iterations = SIZE - x;
			} else if (x == 0 && y == SIZE - 1) {
				iterations = y;
				y--;
			} else if (x == 0 && y < SIZE - 1) {
				iterations = y;
				y--;
			}
		}

		return false;
	}


	public static void makeComputerTurn() {
		// FIXME !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		for ( int i=4; i>1;i--) {
			if (CompTurnByRow(COMPUTER, i)) {
				return;
			} else if (CompTurnByCol(COMPUTER, i)) {
				return;
			} else if (CompTurnByMainDiagonal(COMPUTER, i)) {
				return;
			} else if (CompTurnByMainDiagonal(COMPUTER, i)) {
				return;
			} else if (CompTurnSecondaryDiagonal(COMPUTER, i)) {
				return;
			} else if (CompTurnByRow(HUMAN, i)) {
				return;
			} else if (CompTurnByCol(HUMAN, i)) {
				return;
			} else if (CompTurnByMainDiagonal(HUMAN, i)) {
				return;
			} else if (CompTurnByMainDiagonal(HUMAN, i)) {
				return;
			} else if (CompTurnSecondaryDiagonal(HUMAN, i)) {
				return;
			}
		}

		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (gameTable[i][j] == EMPTY) {
					makeTurn(i, j, COMPUTER); // !IMPORTANT
					return;
				}
			}
		}
		// FIXME !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	}

	public static boolean findWinnerByRow(char figure) {
		// FIXME !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		for (int i = 0; i < SIZE; i++) {
			clear();
			for (int j = 0; j < SIZE; j++) {
				if (figure == gameTable[i][j]) {
					addWinningCoordinate(i, j);
				} else if (figure != gameTable[i][j]) {
					clear();
				}
				if (count == WIN_COUNT) {
					markWinningCombinationByRedColor();
					return true;
				}
			}
		}
		return false;
		// FIXME !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	}

	public static boolean findWinnerByCol(char figure) {
		for (int i = 0; i < SIZE; i++) {
			clear();
			for (int j = 0; j < SIZE; j++) {
				if (figure == gameTable[j][i]) {
					addWinningCoordinate(j, i);
				} else if (figure != gameTable[j][i]) {
					clear();
				}
				if (count == WIN_COUNT) {
					markWinningCombinationByRedColor();
					return true;
				}
			}
		}
		return false;
	}

	public static boolean findWinnerByMainDiagonal(char figure) {
		int start_point = SIZE - WIN_COUNT;
		int x = start_point;
		int iterations = SIZE - x;
		int y = 0;
		for (int i = 0; i < (start_point) * 2 + 1; i++) {
			int j = x;
			int k = y;
			for (int n = 0; n < iterations; n++) {
				if (figure == gameTable[j][k]) {
					addWinningCoordinate(j, k);
				} else if (figure != gameTable[j][k]) {
					clear();
				}
				k++;
				j++;
				if (count == WIN_COUNT) {
					markWinningCombinationByRedColor();
					return true;
				}
			}
			if (x > 0) {
				x--;
				y = 0;
				iterations = SIZE - x;
			} else if (x == 0 && y == 0) {
				y++;
				iterations = SIZE - y;
			} else if (x == 0 && y > 0) {
				y++;
				iterations = SIZE - y;
			}
		}

		return false;
	}

	public static boolean findWinnerBySeconaryDiagonal(char figure) {
		int start_point = SIZE - WIN_COUNT;
		int x = start_point;
		int iterations = SIZE - x;
		int y = SIZE - 1;
		for (int i = 0; i < (start_point) * 2 + 1; i++) {
			int j = x;
			int k = y;
			for (int n = 0; n < iterations; n++) {
				if (figure == gameTable[j][k]) {
					addWinningCoordinate(j, k);
				} else if (figure != gameTable[j][k]) {
					clear();
				}
				j++;
				k--;
				if (count == WIN_COUNT) {
					markWinningCombinationByRedColor();
					return true;
				}
			}
			if (x > 0) {
				x--;
				y = SIZE - 1;
				iterations = SIZE - x;
			} else if (x == 0 && y == SIZE - 1) {
				iterations = y;
				y--;
			} else if (x == 0 && y < SIZE - 1) {
				iterations = y;
				y--;
			}
		}

		return false;
	}

	public static boolean findWinner(char figure) {
		// FIXME !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		if (findWinnerByRow(figure)) {
			return true;
		} else if (findWinnerByCol(figure)) {
			return true;
		} else if (findWinnerByMainDiagonal(figure)) {
			return true;
		} else if (findWinnerBySeconaryDiagonal(figure)) {
			return true;
		}
		return false;
		// FIXME !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	}

	public static void main(String[] args) {
		GUIGomoku.main(args);
	}
}