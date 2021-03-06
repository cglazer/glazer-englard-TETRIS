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
	private Piece nextPieceShape;
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
	private PriorityQueue queue;
	private int level;
	private int numCells;
	private int speed;

	public TetrisGrid() {
		this.numRows = 20;
		this.numCols = 10;
		this.numCells = 0;
		this.level = 1;
		this.speed = 200;
		this.labels = new JLabel[this.numRows][this.numCols];
		this.map = new HashMap<JLabel, Boolean>();
		this.labelSet = new HashSet<JLabel>();
		this.numDeletedRows = 0;
		this.queue = new PriorityQueue();
		setLayout(new GridLayout(this.numRows, this.numCols));

		for (int i = 0; i < this.numRows; i++) {
			for (int x = 0; x < this.numCols; x++) {
				this.labels[i][x] = new JLabel();
				add(this.labels[i][x]);
				this.labels[i][x].setBackground(Color.BLACK);
				this.labels[i][x].setOpaque(true);
				this.labels[i][x].setBorder(BorderFactory
						.createLineBorder(Color.DARK_GRAY));
			}
		}
	}

	public void startGame() {
		this.queue.reset();
		chooseShape();
		chooseShape();
		chooseShape();
		this.lines = 0;
		this.score = 0;
		this.map.clear();
		this.labelSet.clear();
		for (int i = 0; i < numRows; i++) {
			for (int x = 0; x < numCols; x++) {
				this.labels[i][x].setBackground(Color.BLACK);
				this.labels[i][x].setOpaque(true);
				this.map.put(labels[i][x], false);
			}
		}
		nextShape();
	}

	public void runGame() {
		eraseOldPeice();
		drawCurrentPiece();
		checkPieceDone();
	}

	public boolean checkMap(int mapRow, int mapCol) {
		if (this.map.get(this.labels[mapRow][mapCol])) {
			return true;
		}
		return false;
	}

	public boolean checkSet(int setRow, int setCol) {
		if (this.labelSet.contains(this.labels[setRow][setCol])) {
			return true;
		}
		return false;
	}

	public void moveLeft() {
		if (row1 > 0) {
			if (pieceShape.moveLeftValidate()
					&& (!checkMap(row1, column1 - 1) || checkSet(row1,
							column1 - 1))
					&& (!checkMap(row2, column2 - 1) || checkSet(row2,
							column2 - 1))
					&& (!checkMap(row3, column3 - 1) || checkSet(row3,
							column3 - 1))
					&& (!checkMap(row4, column4 - 1) || checkSet(row4,
							column4 - 1))) {
				this.pieceShape.moveLeft();
				refreshColumnValues();
			}
		}
	}

	public void moveRight() {
		if (row1 > 0) {
			if (pieceShape.moveRightValidate()
					&& (!checkMap(row1, column1 + 1) || checkSet(row1,
							column1 + 1))
					&& (!checkMap(row2, column2 + 1) || checkSet(row2,
							column2 + 1))
					&& (!checkMap(row3, column3 + 1) || checkSet(row3,
							column3 + 1))
					&& (!checkMap(row4, column4 + 1) || checkSet(row4,
							column4 + 1))) {
				this.pieceShape.moveRight();
				refreshColumnValues();
			}
		}
	}

	public void moveDownFast() {
		if (row1 > 0) {
			if (row1 + 2 < numRows
					&& row2 + 2 < numRows
					&& row3 + 2 < numRows
					&& row4 + 2 < numRows
					&& (!checkMap(row1 + 2, column1) || checkSet(row1 + 2,
							column1))
					&& (!checkMap(row2 + 2, column2) || checkSet(row2 + 2,
							column2))
					&& (!checkMap(row3 + 2, column3) || checkSet(row3 + 2,
							column3))
					&& (!checkMap(row4 + 2, column4) || checkSet(row4 + 2,
							column4))
					&& (!checkMap(row1 + 1, column1) || checkSet(row1 + 1,
							column1))
					&& (!checkMap(row2 + 1, column2) || checkSet(row2 + 1,
							column2))
					&& (!checkMap(row3 + 1, column3) || checkSet(row3 + 1,
							column3))
					&& (!checkMap(row4 + 1, column4) || checkSet(row4 + 1,
							column4))) {
				this.pieceShape.moveDownFast();
				refreshRowValues();
				score += 2;
			}
		}
	}

	public void turn() {
		if (row1 > 0 && row1 + 1 < numRows && row2 + 1 < numRows
				&& row4 + 1 < numRows) {
			this.pieceShape.turn();
			refreshRowValues();
			refreshColumnValues();
			if ((checkMap(row1, column1) && !checkSet(row1, column1))
					|| (checkMap(row2, column2) && !checkSet(row2, column2))
					|| (checkMap(row3, column3) && !checkSet(row3, column3))
					|| (checkMap(row4, column4) && !checkSet(row4, column4))) {
				this.pieceShape.unTurn();
				refreshRowValues();
				refreshColumnValues();
			}
		}
	}

	public void nextShape() {
		this.pieceShape = queue.dequeue();
		chooseShape();
		this.color = this.pieceShape.getColor();
		refreshRowValues();
		refreshColumnValues();
	}

	public void chooseShape() {
		Random newShape = new Random();
		int numShape = newShape.nextInt(7);
		switch (numShape) {
		case 0:
			this.nextPieceShape = new LPiece(numCols, numRows);
			break;
		case 1:
			this.nextPieceShape = new IPiece(numCols, numRows);
			break;
		case 2:
			this.nextPieceShape = new OPiece(numCols, numRows);
			break;
		case 3:
			this.nextPieceShape = new JPiece(numCols, numRows);
			break;
		case 4:
			this.nextPieceShape = new TPiece(numCols, numRows);
			break;
		case 5:
			this.nextPieceShape = new ZPiece(numCols, numRows);
			break;
		case 6:
			this.nextPieceShape = new SPiece(numCols, numRows);
			break;
		}
		queue.enqueue(this.nextPieceShape);
	}

	public Piece getNextShape() {
		return queue.peek();

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
			labels[0][i].setBackground(Color.BLACK);
		}

		if (lines % 10 == 0) {
			moveUpLevel();
		}
	}

	public void checkFinishedRow() {
		this.numDeletedRows = 0;
		boolean fullLine = true;
		int numSubPiece = 0;
		int checkCol;
		int x = 0;
		for (int i = 0; i < numRows; i++) {
			checkCol = 0;
			fullLine = true;
			while (fullLine) {
				if (!map.get(labels[i][checkCol])) {
					fullLine = false;
				} else {
					numSubPiece++;
					if (checkCol == 9) {
						for (x = 0; x < numCols; x++) {
							labels[i][x].setBackground(Color.black);
							map.put(labels[i][x], false);
						}
						deleteRows(i);
						this.numDeletedRows++;
						repaint();
						fullLine = false;
						numSubPiece -= 10;
					}
					checkCol++;
				}
			}
			// finish off checking rest of the spaces to see if the whole board
			// is empty
			for (int col = x; col < numCols; col++) {
				if (map.get(labels[i][col])) {
					numSubPiece++;
				}
			}
		}
		finishRowScore(numSubPiece);
	}

	public void finishRowScore(int numSubPiece) {
		if (numSubPiece == 0) {
			score += 2000 * level;
		} else {
			switch (this.numDeletedRows) {
			case 1:
				this.score += 40 * level;
				break;
			case 2:
				this.score += 100 * level;
				break;
			case 3:
				this.score += 300 * level;
				break;
			case 4:
				this.score += 1200 * level;
				break;
			}
		}
	}

	public int getScore() {
		return score;
	}

	public boolean checkGameOver() {
		for (int i = 0; i < numCols; i++) {
			if (checkMap(0, i) && checkMap(1, i) && !checkSet(1, i)) {
				return true;
			}
		}
		return false;
	}

	public void eraseOldPeice() {
		// TODO Auto-generated method stub
		// if piece is on board it gets erased!!
		if (row1 > 0) {
			eraseSubOldPeice(coloredRow1, coloredColumn1);
		}
		if (row2 > 0) {
			eraseSubOldPeice(coloredRow2, coloredColumn2);
		}
		if (row3 > 0) {
			eraseSubOldPeice(coloredRow3, coloredColumn3);
		}
		if (row4 > 0) {
			eraseSubOldPeice(coloredRow4, coloredColumn4);
		}
		labelSet.clear();
	}

	public void eraseSubOldPeice(int eraseRow, int eraseCol) {
		labels[eraseRow][eraseCol].setBackground(Color.BLACK);
		map.put(labels[eraseRow][eraseCol], false);
	}

	public void drawCurrentPiece() {
		// TODO Auto-generated method stub
		// draws current piece
		if (row1 >= 0) {
			subDrawPiece(row1, column1);
		}
		if (row2 >= 0) {
			subDrawPiece(row2, column2);

		}
		if (row3 >= 0) {
			subDrawPiece(row3, column3);
		}
		if (row4 >= 0) {
			subDrawPiece(row4, column4);
		}
		savePieceInfo();
		repaint();
		pieceShape.moveDown();
		refreshRowValues();

	}

	public void savePieceInfo() {
		coloredRow1 = row1;
		coloredRow2 = row2;
		coloredRow3 = row3;
		coloredRow4 = row4;
		coloredColumn1 = column1;
		coloredColumn2 = column2;
		coloredColumn3 = column3;
		coloredColumn4 = column4;
	}

	public void subDrawPiece(int drawRow, int drawCol) {
		labels[drawRow][drawCol].setBackground(color);
		labels[drawRow][drawCol].setOpaque(true);
		map.put(labels[drawRow][drawCol], true);
		labelSet.add(labels[drawRow][drawCol]);
	}

	public void checkPieceDone() {
		// TODO Auto-generated method stub
		if (row1 == numRows || row2 == numRows || row3 == numRows
				|| row4 == numRows
				|| (checkMap(row1, column1) && !checkSet(row1, column1))
				|| (checkMap(row2, column2) && !checkSet(row2, column2))
				|| (checkMap(row3, column3) && !checkSet(row3, column3))
				|| (checkMap(row4, column4) && !checkSet(row4, column4))) {
			score += 10;
			checkFinishedRow();
			nextShape();
		}
	}

	private void moveUpLevel() {
		this.level++;
		this.speed -= 20;
	}

	public long getSpeed() {
		return this.speed;
	}

	public int getLines() {
		return this.lines;
	}

	public int getLevel() {
		return this.level;
	}

	public void fallDown() {
		numCells = 0;
		if (row1 > 0) {
			while (row1 + 1 < numRows
					&& row2 + 1 < numRows
					&& row3 + 1 < numRows
					&& row4 + 1 < numRows
					&& (!checkMap(row1 + 1, column1) || checkSet(row1 + 1,
							column1))
					&& (!checkMap(row2 + 1, column2) || checkSet(row2 + 1,
							column2))
					&& (!checkMap(row3 + 1, column3) || checkSet(row3 + 1,
							column3))
					&& (!checkMap(row4 + 1, column4) || checkSet(row4 + 1,
							column4))) {
				this.pieceShape.moveDown();
				refreshRowValues();
				this.numCells++;
			}
		}
		score += (2 * numCells);
	}

}
