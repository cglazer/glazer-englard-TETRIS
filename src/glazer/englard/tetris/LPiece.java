package glazer.englard.tetris;

import java.awt.Color;

public class LPiece extends Piece implements PieceInterface {

	public LPiece() {
		super();
		super.color = Color.ORANGE;
		startPosition();
	}

	private void startPosition() {
		row1 = -1;
		row2 = 0;
		row3 = 0;
		row4 = 0;
		column1 = 5;
		column2 = 3;
		column3 = 4;
		column4 = 5;

	}

	@Override
	public void turn() {
		int turnType = super.turnCounter % 4;
		switch (turnType) {
		case 1:
			// c1 stays, 234 all c3, r3c3 stay
			super.row1 = super.row1 + 2;
			super.row2 = super.row2 - 1;
			super.row4 = super.row4 + 1;
			super.column2 = super.column3;
			super.column4 = super.column3;
			break;
		case 2:
			// r1 stays r234 allr3
			super.row2 = super.row3;
			super.row4 = super.row3;
			super.column1 = super.column1 - 2;
			super.column2 = super.column2 - 1;
			super.column4 = super.column4 + 1;

			break;
		case 3:
			super.row1 = super.row1 - 2;
			super.row2 = super.row2 - 1;
			// c3r3 stays
			super.row4 = super.row4 + 1;
			// c1 stays, c234 all c3
			super.column2 = super.column3;
			super.column4 = super.column3;

			break;
		case 0:
			// r3 stays and r234 all r3, r1 stays
			super.row2 = super.row3;
			super.row4 = super.row3;
			super.column1 = super.column1 + 2;
			super.column2 = super.column2 - 1;
			super.column4 = super.column4 + 1;
			break;

		}
		super.turnCounter++;

	}

}
