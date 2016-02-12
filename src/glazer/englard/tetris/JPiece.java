package glazer.englard.tetris;

import java.awt.Color;

public class JPiece extends Piece {
	public JPiece(int maxC) {
		super(maxC);
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
		if (super.row1 == -1) {
			return;
		}
		int turnType = super.turnCounter % 4;
		switch (turnType) {
		case 1:
			// validate Turn - not go off the bottom when turns
			if (super.row4 + 1 > super.MaxRow) {
				return;
			}
			// r1 same, 3 stays same ,c234 all c3
			super.row2 -= 1;
			super.row4 += 1;
			super.column1 += 2;
			super.column2 = super.column3;
			super.column4 = super.column3;
			break;
		case 2:
			// 3 stays, c1 stays, 24 go to r3
			super.row1 += 2;
			super.row2 = super.row3;
			super.row4 = super.row3;
			super.column2 += 1;
			super.column4 -= 1;
			break;
		case 3:
			// 3 stays, c234 all c3
			super.row2 += 1;
			super.row4 -= 1;
			super.column1 -= 2;
			super.column2 = super.column3;
			super.column4 = super.column3;
			break;
		case 0:
			super.row1 -= 2;
			super.row2 = super.row3;
			super.row4 = super.row3;
			super.column2 -= 1;
			super.column4 += 1;
			break;
		}
		super.turnCounter++;
		while (super.column4 > super.MaxColumn) {
			super.moveLeft();
		}
		while (super.column4 < 0) {
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

	@Override
	public void unTurn() {

		super.turnCounter--;
		int turnType = super.turnCounter % 4;
		switch (turnType) {
		case 1:
			super.row2 += 1;
			super.row4 -= 1;
			super.column1 -= 2;
			super.column2 -=1;
			super.column4 +=1;
			break;
		case 2:
			// 3 stays, c1 stays, 24 go to r3
			super.row1 -= 2;
			super.row2 -=1;
			super.row4 +=1;
			super.column2 -= 1;
			super.column4 += 1;
			break;
		case 3:
			// 3 stays, c234 all c3
			super.row2 -= 1;
			super.row4 += 1;
			super.column1 -= 2;
			super.column2 +=1;
			super.column4 -=1 ;
			break;
		case 0:
			super.row1 += 2;
			super.row2 +=1 ;
			super.row4 -=1 ;
			super.column2 += 1;
			super.column4 -= 1;
			break;

		}
		
		// validate that pieces did not go off the board
		while (super.column1 < 0) {
			super.moveRight();
		}

	}

	
}
