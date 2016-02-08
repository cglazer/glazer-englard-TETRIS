package glazer.englard.tetris;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TetrisGrid extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel[][] labels;
	private HashMap<JLabel, Boolean> map;
	private HashSet<JLabel> labelSet;
	private Piece pieceShape;
	private Color color;
	private int numRows;
	private int numCols;
	private int coloredRow1;
	private int coloredRow2;
	private int coloredRow3;
	private int coloredRow4;
	private int coloredColumn1;
	private int coloredColumn2;
	private int coloredColumn3;
	private int coloredColumn4;
	private int row1;
	private int row2;
	private int row3;
	private int row4;
	private int column1;
	private int column2;
	private int column3;
	private int column4;
	private int numDeletedRows;
	private int score;
	private int lines;

	public TetrisGrid() {
		this.numRows = 30;
		this.numCols = 10;
		this.labels = new JLabel[this.numRows][this.numCols];
		this.map = new HashMap<JLabel, Boolean>();
		this.labelSet = new HashSet<JLabel>();
		this.numDeletedRows = 0;

		setLayout(new GridLayout(this.numRows, this.numCols));
		for (int i = 0; i < this.numRows; i++) {
			for (int x = 0; x < this.numCols; x++) {
				this.labels[i][x] = new JLabel();
				add(this.labels[i][x]);
				this.labels[i][x].setBorder(BorderFactory
						.createLineBorder(Color.BLACK));
			}
		}
	}

	public void startGame() {
		this.lines = 0;
		this.score = 0;
		this.map.clear();
		this.labelSet.clear();
		for (int i = 0; i < numRows; i++) {
			for (int x = 0; x < numCols; x++) {
				this.labels[i][x].setBackground(Color.LIGHT_GRAY);
				this.labels[i][x].setOpaque(true);
				this.map.put(labels[i][x], false);
			}
		}
		chooseShape();
	}

	public void runGame() {
		eraseOldPeice();
		drawCurrentPiece();
		checkPieceDone();
	}

	public void moveLeft() {
		//error when you turn the Z piece then the 4 is messed up - it will b false
		//maybe because the turn method does not add/change anything to labelSet or map...
		System.out.println((!map.get(labels[row4][column4 - 1]) || labelSet
						.contains(labels[row4][column4])));
		if (pieceShape.moveLeftValidate()
				&& (!map.get(labels[row1][column1 - 1]) || labelSet
						.contains(labels[row1][column1]))
				&& (!map.get(labels[row2][column2 - 1]) || labelSet
						.contains(labels[row2][column2]))
				&& (!map.get(labels[row3][column3 - 1]) || labelSet
						.contains(labels[row3][column3]))
				&& (!map.get(labels[row4][column4 - 1]) || labelSet
						.contains(labels[row4][column4]))) {
			
			this.pieceShape.moveLeft();
			refreshColumnValues();
		}
	}

	public void moveRight() {
		if (pieceShape.moveRightValidate()
				&& (!map.get(labels[row1][column1 + 1]) || labelSet
						.contains(labels[row1][column1]))
				&& (!map.get(labels[row2][column2 + 1]) || labelSet
						.contains(labels[row2][column2]))
				&& (!map.get(labels[row3][column3 + 1]) || labelSet
						.contains(labels[row3][column3]))
				&& (!map.get(labels[row4][column4 + 1]) || labelSet
						.contains(labels[row4][column4]))) {
			this.pieceShape.moveRight();
			refreshColumnValues();
		}
	}

	public void moveDownFast() {
		if (row1 + 2 < numRows && row2 + 2 < numRows && row3 + 2 < numRows
				&& row4 + 2 < numRows && !map.get(labels[row1 + 2][column1])
				&& !map.get(labels[row2 + 2][column2])
				&& !map.get(labels[row3 + 2][column3])
				&& !map.get(labels[row4 + 2][column4])) {
			this.pieceShape.moveDownFast();
			refreshRowValues();
			score += 2;
		}
	}

	public void turn() {
		this.pieceShape.turn();
		refreshRowValues();
		refreshColumnValues();
	}

	public void chooseShape() {
		Random newShape = new Random();
		int numShape = newShape.nextInt(7);
		switch (4) {
		case 0:
			this.pieceShape = new LPiece(this.numCols - 1);
			break;
		case 1:
			this.pieceShape = new IPiece(this.numCols - 1);
			break;
		case 2:
			this.pieceShape = new OPiece(this.numCols - 1);
			break;
		case 3:
			this.pieceShape = new JPiece(this.numCols - 1);
			break;
		case 4:
			this.pieceShape = new TPiece(this.numCols - 1);
			break;
		case 5:
			this.pieceShape = new ZPiece(this.numCols - 1);
			break;

		case 6:
			this.pieceShape = new SPiece(this.numCols - 1);
			break;
		}
		this.color = this.pieceShape.getColor();
		refreshRowValues();
		refreshColumnValues();

	}

	private void refreshColumnValues() {
		column1 = pieceShape.getColumn1();
		column2 = pieceShape.getColumn2();
		column3 = pieceShape.getColumn3();
		column4 = pieceShape.getColumn4();

	}

	private void refreshRowValues() {

		row1 = pieceShape.getRow1();
		row2 = pieceShape.getRow2();
		row3 = pieceShape.getRow3();
		row4 = pieceShape.getRow4();

	}

	private void deleteRows(int cRow) {
		// TODO Auto-generated method stub
		this.lines++;
		for (int i = cRow; i > 0; i--) {
			for (int x = 0; x < numCols; x++) {
				labels[i][x].setBackground(labels[i - 1][x].getBackground());
				map.put(labels[i][x], map.get(labels[i - 1][x]));
			}
		}
		for (int i = 0; i < numCols; i++) {
			map.put(labels[0][i], false);
			labels[0][i].setBackground(Color.LIGHT_GRAY);
		}
	}

	public void checkFinishedRow() {
		this.numDeletedRows = 0;
		boolean fullLine = true;
		int checkCol;
		for (int i = 0; i < numRows; i++) {
			checkCol = 0;
			fullLine = true;
			while (fullLine) {
				if (!map.get(labels[i][checkCol])) {
					fullLine = false;
				} else {
					if (checkCol == 9) {
						for (int x = 0; x < numCols; x++) {
							labels[i][x].setBackground(Color.LIGHT_GRAY);
							map.put(labels[i][x], false);
						}
						deleteRows(i);
						this.numDeletedRows++;
						repaint();
						fullLine = false;
					}
					checkCol++;
				}
			}
		}
		switch (this.numDeletedRows) {
		case 1:
			this.score += 40;
			break;
		case 2:
			this.score += 100;
			break;
		case 3:
			this.score += 300;
			break;
		case 4:
			this.score += 1200;
			break;
		}
	}

	public int getScore() {
		return score;
	}

	public boolean checkGameOver() {
		for (int i = 0; i < numCols; i++) {
			if (map.get(labels[0][i]) && map.get(labels[1][i])
					&& !labelSet.contains(labels[1][i])) {
				return true;
			}
		}
		return false;
	}

	public void eraseOldPeice() {
		// TODO Auto-generated method stub
		if (row1 > 0) {
			labels[coloredRow1][coloredColumn1].setBackground(Color.LIGHT_GRAY);
			map.put(labels[coloredRow1][coloredColumn1], false);
		}
		if (row2 > 0) {
			labels[coloredRow2][coloredColumn2].setBackground(Color.LIGHT_GRAY);
			map.put(labels[coloredRow2][coloredColumn2], false);
		}
		if (row3 > 0) {
			labels[coloredRow3][coloredColumn3].setBackground(Color.LIGHT_GRAY);
			map.put(labels[coloredRow3][coloredColumn3], false);
		}
		if (row4 > 0) {
			labels[coloredRow4][coloredColumn4].setBackground(Color.LIGHT_GRAY);
			map.put(labels[coloredRow4][coloredColumn4], false);
		}
		labelSet.clear();
	}

	public void drawCurrentPiece() {
		// TODO Auto-generated method stub
		if (row1 >= 0) {
			labels[row1][column1].setBackground(color);
			labels[row1][column1].setOpaque(true);
			map.put(labels[row1][column1], true);
			labelSet.add(labels[row1][column1]);
		}
		if (row2 >= 0) {
			labels[row2][column2].setBackground(color);
			labels[row2][column2].setOpaque(true);
			map.put(labels[row2][column2], true);
			labelSet.add(labels[row2][column2]);
		}
		if (row3 >= 0) {
			labels[row3][column3].setBackground(color);
			labels[row3][column3].setOpaque(true);
			map.put(labels[row3][column3], true);
			labelSet.add(labels[row3][column3]);
		}
		if (row4 >= 0) {
			labels[row4][column4].setBackground(color);
			labels[row4][column4].setOpaque(true);
			map.put(labels[row4][column4], true);
			labelSet.add(labels[row4][column4]);
		}
		coloredRow1 = row1;
		coloredRow2 = row2;
		coloredRow3 = row3;
		coloredRow4 = row4;
		coloredColumn1 = column1;
		coloredColumn2 = column2;
		coloredColumn3 = column3;
		coloredColumn4 = column4;
		repaint();
		pieceShape.moveDown();
		refreshRowValues();
	}

	public void checkPieceDone() {
		// TODO Auto-generated method stub
		if ((map.get(labels[row1][column1]) && !labelSet
				.contains(labels[row1][column1]))
				|| (map.get(labels[row2][column2]) && !labelSet
						.contains(labels[row2][column2]))
				|| (map.get(labels[row3][column3]) && !labelSet
						.contains(labels[row3][column3]))
				|| (map.get(labels[row4][column4]) && !labelSet
						.contains(labels[row4][column4]))
				|| row1 == 28
				|| row2 == 28 || row3 == 28 || row4 == 28) {
			checkFinishedRow();
			chooseShape();
		}
	}

	public int getLines() {
		// TODO Auto-generated method stub
		return this.lines;
	}

}