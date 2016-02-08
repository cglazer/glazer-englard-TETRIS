package glazer.englard.tetris;

import java.awt.Color;

public class LPiece extends Piece implements PieceInterface {

	public LPiece(int maxC) {
		super(maxC);
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
		int turnType = super.turnCounter % 4;
		switch (turnType) {
		case 1:
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

	}

	@Override
	public boolean moveRightValidate() {
		//right most piece is 1 or 4
		return (super.column1 < super.MaxColumn && super.column4< super.MaxColumn);
	}

	@Override
	public boolean moveLeftValidate() {
		//left most piece s 4 or 1
		return (super.column1 > 0 && super.column4 > 0);
	}
}
