package glazer.englard.tetris;

import java.awt.Color;

public class TPiece extends Piece {
	public TPiece(int maxC) {
		super(maxC);
		super.color = new Color(128, 0, 128); // purple
		startPosition();
	}

	private void startPosition() {
		super.row1 = -1;
		super.row2 = 0;
		super.row3 = 0;
		super.row4 = 0;
		super.column1 = 5;
		super.column2 = 4;
		super.column3 = 5;
		super.column4 = 6;

	}

	@Override
	public void turn() {
		if (super.row1 == -1) {
			return;
		}
		int turnType = super.turnCounter % 4;
		switch (turnType) {
		case 1:
			// validate
			if (super.row2 + 1 > super.MaxRow) {
				return; // dont turn
			}
			// moving one of the sub pieces changes all whole postion
			// move bottom left to under middle
			// 1 to 1
			// 2 3 4 3 4
			// 2
			super.column2 += 1;
			super.row2 += 1;
			break;
		case 2:
			// top middle move to top left
			// 1 to
			// 3 4 1 3 4
			// 2 2
			super.row1 += 1;
			super.column1 -= 1;
			break;
		case 3:
			// right mid goes to top mid
			// to 4
			// 1 3 4 1 3
			// 2 2
			super.row4 -= 1;
			super.column4 -= 1;

			break;
		case 0:
			// 4 not 4 bring back to original 1
			// 1 3 1 3 2 2 3 4
			// 2
			// 3 is good move 4 down to right, then move one up, then 2 into old
			// 1
			super.row1 -= 1;
			super.row2 = super.row3;
			super.row4 = super.row3;
			super.column1 += 1;
			super.column2 -= 1;
			super.column4 += 1;
			break;

		}

		super.turnCounter++;
		// validation that piece does not go off the sides of the board
		while (super.column4 > super.MaxColumn) {
			super.moveLeft();
		}
		while (super.column1 < 0) {
			super.moveRight();
		}

	}

	@Override
	public boolean moveRightValidate() {
		// 4 is alwasy right most piece
		return super.column4 < super.MaxColumn;
	}

	@Override
	public boolean moveLeftValidate() {
		// 2 or 1 is right most
		return (super.column2 > 0 && super.column1 > 0);
	}

	@Override
	public void unTurn() {

		super.turnCounter--;
		int turnType = super.turnCounter % 4;
		switch (turnType) {
		case 1:
			// validate
			if (super.row2 + 1 > super.MaxRow) {
				return; // dont turn
			}
			
			super.column2 -= 1;
			super.row2 -= 1;
			break;
		case 2:
			
			super.row1 -= 1;
			super.column1 += 1;
			break;
		case 3:
			// right mid goes to top mid
			super.row4 += 1;
			super.column4 += 1;

			break;
		case 0:
			
			super.row1 += 1;
			super.row2 +=1;
			super.row4 -=1 ;
			super.column1 -= 1;
			super.column2 += 1;
			super.column4 -= 1;
			break;

		}

		// validation that piece does not go off the sides of the board
		while (super.column4 > super.MaxColumn) {
			super.moveLeft();
		}
		while (super.column1 < 0) {
			super.moveRight();
		}

	}

}
