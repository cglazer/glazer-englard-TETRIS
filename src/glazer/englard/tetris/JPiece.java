package glazer.englard.tetris;

import java.awt.Color;

public class JPiece extends Piece implements PieceInterface {
	public JPiece() {
		super();
		super.color = Color.BLUE;
		startPosition();
	}

	private void startPosition() {
		super.row1 = -1;
		super.row2 = 0;
		super.row3 = 0;
		super.row4 = 0;
		super.column1 = 3;
		super.column2 = 3;
		super.column3 = 4;
		super.column4 = 5;

	}

	@Override
	public void turn() {
		int turnType = super.turnCounter % 4;
		switch (turnType) {
		case 1:
			// r1 same, r3 same
			super.row2 -= 1;
			super.row4 += 1;
			super.column1 += 2;
			// c234 all c3
			super.column2 = super.column3;
			super.column4 = super.column3;
			break;
		case 2:
			super.row1 += 2;
			// r234 all r3
			super.row2 = super.row3;
			super.row4 = super.row3;
			// c1 stays c3 stays
			super.column2 += 1;
			super.column4 -= 1;
			break;
		case 3:
			super.row1 += 1;
			super.row2 += 1;
			// r3 stays
			super.row4 -= 1;
			// c234 all c3
			super.column2 = super.column3;
			super.column4 = super.column3;
			break;
		case 0:
			super.row1 -= 2;
			super.row2 -= 1;
			super.row4 += 1;
			super.column2 -= 1;
			super.column4 += 1;
			break;
		}
		super.turnCounter++;

	}
}
