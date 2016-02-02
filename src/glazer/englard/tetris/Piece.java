package glazer.englard.tetris;

import java.awt.Color;
import java.util.Random;

public class Piece {

	// the shape and color of the piece are randomly generated
	// each piece is made of 4 subunits

	//private char shape;
	protected Color color;
	protected int row1;
	protected int column1;
	protected int row2;
	protected int column2;
	protected int row3;
	protected int column3;
	protected int row4;
	protected int column4;
	protected int turnCounter;

	public Piece() {
	//	generateShape();
	//	setStartPosition(); // based on the shape, the boxes in grid that are
							// highlighed
		this.turnCounter = 0;
	}

	public int getRow1() {
		return row1;
	}

	public void setRow1(int row1) {
		this.row1 = row1;
	}

	public int getColumn1() {
		return column1;
	}

	public void setColumn1(int column1) {
		this.column1 = column1;
	}

	public int getRow2() {
		return row2;
	}

	public void setRow2(int row2) {
		this.row2 = row2;
	}

	public int getColumn2() {
		return column2;
	}

	public void setColumn2(int column2) {
		this.column2 = column2;
	}

	public int getRow3() {
		return row3;
	}

	public void setRow3(int row3) {
		this.row3 = row3;
	}

	public int getColumn3() {
		return column3;
	}

	public void setColumn3(int column3) {
		this.column3 = column3;
	}

	public int getRow4() {
		return row4;
	}

	public void setRow4(int row4) {
		this.row4 = row4;
	}

	public int getColumn4() {
		return column4;
	}

	public void setColumn4(int column4) {
		this.column4 = column4;
	}

	//public char getShape() {
	//	return shape;
	//}

	public Color getColor() {
		return color;
	}
	public void moveRight() {
		column1 += 1;
		column2 += 1;
		column3 += 1;
		column4 += 1;

	}

	public void moveLeft() {
		column1 -= 1;
		column2 -= 1;
		column3 -= 1;
		column4 -= 1;

	}

	public void moveDown() {

		row1 -= 1;
		row2 -= 1;
		row3 -= 1;
		row4 -= 1;

	}

	
}