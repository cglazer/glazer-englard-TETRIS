package glazer.englard.tetris;

import java.awt.Color;

public class LPiece extends Piece {

	public LPiece(int maxC, int maxR) {
		super(maxC, maxR);
		super.color = Color.ORANGE;
		startPosition();
	}

	private void startPosition() {
		row1 = -1;
		row2 = 0;
		row3 = 0;
		row4 = 0;
		column1 = 5;
		column2 = 5;
		column3 = 4;
		column4 = 3;

	}

	@Override
	public void turn() {
		// dont turn if part of the piece is above the board
		if (super.row1 == -1) {
			return;
		}
		int turnType = super.turnCounter % 4;
		switch (turnType) {
		case 1:
			// make sure piece does not go beyon bottom
			if (super.row4 + 1 > super.MaxRow) {
				return; // dont turn
			}
			// c1 stays, 234 all c3, r3c3 stay
			super.row1 += 2;
			super.row2 -= 1;
			super.row4 += 1;
			super.column2 = super.column3;
			super.column4 = super.column3;
			break;
		case 2:
			// r1 stays r234 allr3
			super.row2 = super.row3;
			super.row4 = super.row3;
			super.column1 -= 2;
			super.column2 -= 1;
			super.column4 += 1;
			break;
		case 3:
			// 3 stays, c1 stays
			super.row1 -= 2;
			super.row2 -= 1;
			super.row4 += 1;
			super.column2 = super.column3;
			super.column4 = super.column3;
			break;
		case 0:
			// 3 stays , r1 stays
			super.row2 = super.row3;
			super.row4 = super.row3;
			super.column1 += 2;
			super.column2 += 1;
			super.column4 -= 1;
			break;

		}
		super.turnCounter++;
		// validate that pieces did not go off the board
		while (super.column1 < 0) {
			super.moveRight();
		}
	}

	public void unTurn() {
		super.turnCounter--;
		int turnType = super.turnCounter % 4;
		switch (turnType) {
		case 1:
			// c1 stays, 234 all c3, r3c3 stay
			super.row1 -= 2;
			super.row2 += 1;
			super.row4 -= 1;
			super.column2 += 1;
			super.column4 -= 1;
			break;
		case 2:
			// r1 stays r234 allr3
			super.row2 -= 1;
			super.row4 += 1;
			super.column1 += 2;
			super.column2 += 1;
			super.column4 -= 1;
			break;
		case 3:
			// 3 stays, c1 stays
			super.row1 += 2;
			super.row2 += 1;
			super.row4 -= 1;
			super.column2 -= 1;
			super.column4 += 1;
			break;
		case 0:
			// 3 stays , r1 stays
			super.row2 -= 1;
			super.row4 += 1;
			super.column1 -= 2;
			super.column2 -= 1;
			super.column4 += 1;
			break;

		}

		// validate that pieces did not go off the board
		while (super.column1 < 0) {
			super.moveRight();
		}
	}

	@Override
	public boolean moveRightValidate() {
		// right most piece is 1 or 4
		return (super.column1 < super.MaxColumn && super.column4 < super.MaxColumn);
	}

	@Override
	public boolean moveLeftValidate() {
		// left most piece s 4 or 1
		return (super.column1 > 0 && super.column4 > 0);
	}

}
