package glazer.englard.tetris;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;

public class OPiece extends Piece  {

	public OPiece(int maxC) {
		super(maxC);
		super.color = Color.YELLOW;
		super.colorImage = new ImageIcon(new ImageIcon(this.getClass()
				.getResource("./yellowPiece.png")).getImage()
				.getScaledInstance(25, 25, Image.SCALE_SMOOTH));
		super.nextPanelIcon = new ImageIcon(colorImage.getImage().getScaledInstance(37, 25, Image.SCALE_SMOOTH));
		startPosition();
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
