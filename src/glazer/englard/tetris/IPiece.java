package glazer.englard.tetris;

import java.awt.Color;

public class IPiece extends Piece {

	public IPiece(int maxC, int maxR) {
		super(maxC, maxR);
		super.color = new Color(31, 190, 214); // light blue
		startPosition();

	}

	private void startPosition() {

		super.row1 = 0;
		super.row2 = 0;
		super.row3 = 0;
		super.row4 = 0;
		super.column1 = 3;
		super.column2 = 4;
		super.column3 = 5;
		super.column4 = 6;

	}

	public void turn() {

		int turnType = super.turnCounter % 4;
		switch (turnType) {
		case 1:
			// validate Turn - not go off the bottom when turns
			if (super.row4 + 3 > super.MaxRow) {
				return;
			}
			// everything in c3, 3 stays same
			super.row1 += 1;
			super.row2 += 2;
			super.row4 += 3;
			super.column1 = super.column3;
			super.column2 = super.column3;
			super.column4 = super.column3;
			break;
		case 2:
			// everything inr2, 2 stays same
			super.row1 = super.row2;
			super.row3 = super.row2;
			super.row4 = super.row2;
			super.column1 -= 2;
			super.column3 -= 1;
			super.column4 += 1;
			break;
		case 3:
			// everything goes into r3, r3 stays same
			super.row1 -= 2;
			super.row2 -= 1;
			super.row4 += 1;
			super.column1 = super.column3;
			super.column2 = super.column3;
			super.column4 = super.column3;
			break;
		case 0:
			// goes into r1, r1 and c2 stay
			super.row2 = super.row1;
			super.row3 = super.row1;
			super.row4 = super.row1;
			super.column1 -= 1;
			super.column3 += 1;
			super.column4 += 2;
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

	public void unTurn() {
		super.turnCounter--;
		int turnType = super.turnCounter % 4;
		switch (turnType) {
		case 1:

			// everything in c3, 3 stays same
			super.row1 -= 1;
			super.row2 -= 2;
			super.row4 -= 3;
			super.column1 -= 2;
			super.column2 -= 1;
			super.column4 += 1;
			break;
		case 2:
			// everything inr2, 2 stays same
			super.row1 -= 1;
			super.row3 -= 2;
			super.row4 += 1;
			super.column1 += 2;
			super.column3 += 1;
			super.column4 -= 1;
			break;
		case 3:
			// everything goes into r3, r3 stays same
			super.row1 += 2;
			super.row2 += 1;
			super.row4 -= 1;
			super.column1 -= 1;
			super.column2 += 1;
			super.column4 += 2;
			break;
		case 0:
			// goes into r1, r1 and c2 stay
			super.row2 += 1;
			super.row3 += 2;
			super.row4 += 3;
			super.column1 += 1;
			super.column3 -= 1;
			super.column4 -= 2;
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

	@Override
	public boolean moveRightValidate() {
		// piece 4 is always most right or equal to other pieces so check only
		// that piece
		return super.column4 < super.MaxColumn;

	}

	@Override
	public boolean moveLeftValidate() {
		// piece 1 is right most piece always
		return super.column1 > 0;

	}

}
