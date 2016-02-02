package glazer.englard.tetris;

import java.awt.Color;

public class OPiece extends Piece implements PieceInterface {

	public OPiece() {
		super();
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

	

}
