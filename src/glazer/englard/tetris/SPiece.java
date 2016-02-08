package glazer.englard.tetris;

import java.awt.Color;

public class SPiece extends Piece{

	
	
	
	public SPiece(int maxC) {
		super(maxC);
		super.color = Color.GREEN;
		startPosition();
	}

	private void startPosition() {
		super.row1 = -1;
		super.row2 = -1;
		super.row3 = 0;
		super.row4 = 0;
		super.column1 = 4;
		super.column2 = 5;
		super.column3 = 3;
		super.column4 = 4;

	}

	@Override
	public void turn() {
		int turnType = super.turnCounter % 4;
		switch (turnType) {
		case 1:
			// 1 and 4 stay 
			super.row2 += 2;
			super.column3 += 2;
			break;
		case 2:
			// 3 and 4 stay
			super.row1 += 2;
			super.column2 -= 2;
			break;
		case 3:
			//1 and 4 stay
			super.row2 -=2;
			super.column3 -=2;
			break;
		case 0:
			//3 an 4 stay
			super.row1 -=2;
			super.column2 +=2;
		break;

		}
		super.turnCounter++;
	}

	@Override
	public boolean moveRightValidate() {
		//2, 3, or 1 is right most piece
		return (super.column2< super.MaxColumn && super.column1 < super.MaxColumn && super.column3 < super.MaxColumn  );
		
	}

	@Override
	public boolean moveLeftValidate() {
		////1,2, or 3 is right most piece
		return (super.column1 > 0 && super.column2 > 0 && super.column3> 0);
	}


}
