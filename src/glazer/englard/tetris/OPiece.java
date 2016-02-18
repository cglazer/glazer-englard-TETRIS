package glazer.englard.tetris;

import java.awt.Color;

public class OPiece extends Piece  {

	public OPiece(int maxC, int maxR) {
		super(maxC, maxR);
		super.color = Color.YELLOW;
		startPosition();
	}

	private void startPosition() {
		row1 = -1; // you dont see it in beg - may cause error
		row2 = -1;
		row3 = 0;
		row4 = 0;
		column1 = 4;
		column2 = 5;
		column3 = 4;
		column4 = 5;

	}

	@Override
	public void turn() {
		// nothing happens when you turn a OPiece

	}

	@Override
	public boolean moveRightValidate() {
		//2 is always right most
		return super.column2 < super.MaxColumn;
	}

	@Override
	public boolean moveLeftValidate() {
		//1 is always rigth most piece
		return super.column1 > 0;
	}

	@Override
	public void unTurn() {

		// TODO Auto-generated method stub
		turn();
		turn();
		turn();

		//does not do anything since o piece never turns
		

	}

	

}
