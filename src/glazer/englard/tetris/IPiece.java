package glazer.englard.tetris;

import java.awt.Color;

public class IPiece extends Piece implements PieceInterface {

	public IPiece() {
		super();
		super.color = new Color(31, 190, 214);
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
			// everything goes into column 3, c3 does not change and r2 does not
			// change
			super.row1 = super.row1 + 1;
			super.row3 = super.row3 - 1;
			super.row4 = super.row4 - 2;
			super.column1 = super.column3;
			super.column2 = super.column3;
			super.column4 = super.column3;
			break;
		case 2:
			// row2/column2 stays the same
			super.row1 = super.row1 - 1;
			super.row3 = super.row3 + 1;
			super.row4 = super.row4 + 2;
			super.column1 = super.column2;
			super.column3 = super.column2;
			super.column4 = super.column2;

			break;
		case 3:
			// everything goes into r3, r3 stays same
			super.row1 = super.row3;
			super.row2 = super.row3;
			super.row4 = super.row3;
			super.column1 = super.column1 - 2;
			super.column2 = super.column2 - 1;
			super.column4 = super.column4 + 1;
			break;
		case 0:
			// goes inot c2, r3 stays
			super.row1 = super.row1 + 2;
			super.row2 = super.row2 + 1;
			super.row4 = super.row4 - 1;
			super.column1 = super.column2;
			super.column3 = super.column2;
			super.column4 = super.column2;
			break;

		}
		super.turnCounter++;

	}


}
