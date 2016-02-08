package glazer.englard.tetris;

import java.awt.Color;

public class ZPiece extends Piece implements PieceInterface {

	public ZPiece(int maxC) {
		super(maxC);
		super.color = Color.RED;
		startPosition();
	}

	private void startPosition() {
		super.row1 = -1;
		super.row2 = -1;
		super.row3 = 0;
		super.row4 = 0;
		super.column1 = 3;
		super.column2 = 4;
		super.column3 = 4;
		super.column4 = 5;

	}

	@Override
	public void turn() {
		int turnType = super.turnCounter % 4;
		switch (turnType) {
		case 1:
			// 3 and 4 stay, r1 stays, and c2 stays
			super.row2 += 2;
			super.column1 += 2;
			break;
		case 2:
			// 3 and 2 say, r4 and c1 stay
			super.row1 += 2;
			super.column4 -= 2;
			break;
		case 3:
			//3 and 4 stay
			super.row2 -=2;
			super.column1 -=2;
			break;
		case 0:
			//2 and 3 stay 
			super.row1 -=2;
			super.column4 +=2;
			break;

		}
		super.turnCounter++;
	}

	@Override
	public boolean moveRightValidate() {
		//right most piece is 4 1 or 2
		return (super.column2< super.MaxColumn && super.column1 < super.MaxColumn && super.column4 < super.MaxColumn  );
	}

	@Override
	public boolean moveLeftValidate() {
		//left most piece is 4 1 or 2
		return (super.column1 > 0 && super.column2 > 0 && super.column4> 0);
	}

}
