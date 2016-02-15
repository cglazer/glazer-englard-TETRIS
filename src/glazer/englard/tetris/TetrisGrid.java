package glazer.englard.tetris;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

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
		this.speed = 150;
		this.labels = new JLabel[this.numRows + 1][this.numCols];
		this.map = new HashMap<JLabel, Boolean>();
		this.labelSet = new HashSet<JLabel>();
		this.numDeletedRows = 0;
		this.queue = new PriorityQueue();
		// chooseShape();
		// this.setBackground(Color.BLACK);
		setLayout(new GridLayout(this.numRows, this.numCols));
		Border raisedbevel = BorderFactory.createRaisedSoftBevelBorder();
		Border loweredbevel = BorderFactory.createEtchedBorder();
		Border compound = BorderFactory.createCompoundBorder(loweredbevel,
				raisedbevel);
		for (int i = 0; i < this.numRows; i++) {
			for (int x = 0; x < this.numCols; x++) {
				this.labels[i][x] = new JLabel();
				add(this.labels[i][x]);
				this.labels[i][x].setBackground(Color.BLACK);
				this.labels[i][x].setOpaque(true);
				// Border dark =
				// BorderFactory.createLineBorder(Color.DARK_GRAY);
				// Border blackCompound =
				// BorderFactory.createCompoundBorder(compound,dark );
				// this.labels[i][x].setBorder(compound);
				this.labels[i][x].setBorder(BorderFactory
						.createLineBorder(Color.DARK_GRAY));

			}
		}

	}

	public void startGame() {
		this.queue.reset();
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

	public void moveLeft() {
		if (row1 > 0) {
			if (pieceShape.moveLeftValidate()
					&& (!map.get(labels[row1][column1 - 1]) || labelSet
							.contains(labels[row1][column1 - 1]))
					&& (!map.get(labels[row2][column2 - 1]) || labelSet
							.contains(labels[row2][column2 - 1]))
					&& (!map.get(labels[row3][column3 - 1]) || labelSet
							.contains(labels[row3][column3 - 1]))
					&& (!map.get(labels[row4][column4 - 1]) || labelSet
							.contains(labels[row4][column4 - 1]))) {
				this.pieceShape.moveLeft();
				refreshColumnValues();
				System.out.println("move left");
			}
		}
	}

	public void moveRight() {
		if (row1 > 0) {
			if (pieceShape.moveRightValidate()
					&& (!map.get(labels[row1][column1 + 1]) || labelSet
							.contains(labels[row1][column1 + 1]))
					&& (!map.get(labels[row2][column2 + 1]) || labelSet
							.contains(labels[row2][column2 + 1]))
					&& (!map.get(labels[row3][column3 + 1]) || labelSet
							.contains(labels[row3][column3 + 1]))

					&& (!map.get(labels[row4][column4 + 1]) || labelSet
							.contains(labels[row4][column4 + 1]))) {
				this.pieceShape.moveRight();
				refreshColumnValues();
				System.out.println("move right");
			}
		}
	}

	public void moveDownFast() {
		if (row1 > 0) {
			if (row1 + 2 < numRows
					&& row2 + 2 < numRows
					&& row3 + 2 < numRows
					&& row4 + 2 < numRows
					&& (!map.get(labels[row1 + 2][column1]) || labelSet
							.contains(labels[row1 + 2][column1]))
					&& (!map.get(labels[row2 + 2][column2]) || labelSet
							.contains(labels[row2 + 2][column2]))
					&& (!map.get(labels[row3 + 2][column3]) || labelSet
							.contains(labels[row3 + 2][column3]))
					&& (!map.get(labels[row4 + 2][column4]) || labelSet
							.contains(labels[row4 + 2][column4]))
					&& (!map.get(labels[row1 + 1][column1]) || labelSet
							.contains(labels[row1 + 1][column1]))
					&& (!map.get(labels[row2 + 1][column2]) || labelSet
							.contains(labels[row2 + 1][column2]))
					&& (!map.get(labels[row3 + 1][column3]) || labelSet
							.contains(labels[row3 + 1][column3]))
					&& (!map.get(labels[row4 + 1][column4]) || labelSet
							.contains(labels[row4 + 1][column4]))) {
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
			if ((map.get(labels[row1][column1]) && !labelSet
					.contains(labels[row1][column1]))
					|| (map.get(labels[row2][column2]) && !labelSet
							.contains(labels[row2][column2]))
					|| (map.get(labels[row3][column3]) && !labelSet
							.contains(labels[row3][column3]))
					|| (map.get(labels[row4][column4]) && !labelSet
							.contains(labels[row4][column4]))) {
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
			this.nextPieceShape = new LPiece(numCols);
			queue.enqueue(this.nextPieceShape);
			break;
		case 1:
			this.nextPieceShape = new IPiece(numCols);
			queue.enqueue(this.nextPieceShape);
			break;
		case 2:
			this.nextPieceShape = new OPiece(numCols);
			queue.enqueue(this.nextPieceShape);
			break;
		case 3:
			this.nextPieceShape = new JPiece(numCols);
			queue.enqueue(this.nextPieceShape);
			break;
		case 4:
			this.nextPieceShape = new TPiece(numCols);
			queue.enqueue(this.nextPieceShape);
			break;
		case 5:
			this.nextPieceShape = new ZPiece(numCols);
			queue.enqueue(this.nextPieceShape);
			break;
		case 6:
			this.nextPieceShape = new SPiece(numCols);
			queue.enqueue(this.nextPieceShape);
			break;
		}
		// score += 10;

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
			if (map.get(labels[0][i]) && map.get(labels[1][i])
					&& !labelSet.contains(labels[1][i])) {
				return true;
			}
		}
		return false;
	}

	public void eraseOldPeice() {
		// TODO Auto-generated method stub
		// if piece is on board it gets erased!!
		if (row1 > 0) {
			labels[coloredRow1][coloredColumn1].setBackground(Color.BLACK);
			map.put(labels[coloredRow1][coloredColumn1], false);
		}
		if (row2 > 0) {
			labels[coloredRow2][coloredColumn2].setBackground(Color.BLACK);
			map.put(labels[coloredRow2][coloredColumn2], false);
		}
		if (row3 > 0) {
			labels[coloredRow3][coloredColumn3].setBackground(Color.BLACK);
			map.put(labels[coloredRow3][coloredColumn3], false);
		}
		if (row4 > 0) {
			labels[coloredRow4][coloredColumn4].setBackground(Color.BLACK);
			map.put(labels[coloredRow4][coloredColumn4], false);
		}
		labelSet.clear();
	}

	public void drawCurrentPiece() {
		// TODO Auto-generated method stub
		// draws current piece
		if (row1 >= 0) {
			labels[row1][column1].setBackground(color);// .setIcon(new
														// ImageIcon(new
														// ImageIcon(this.getClass().getResource("./purplePiece.png")).getImage().getScaledInstance(27,
														// 27,
														// java.awt.Image.SCALE_SMOOTH)));
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
		if (row1 == numRows
				|| row2 == numRows
				|| row3 == numRows
				|| row4 == numRows
				|| (map.get(labels[row1][column1]) && !labelSet
						.contains(labels[row1][column1]))
				|| (map.get(labels[row2][column2]) && !labelSet
						.contains(labels[row2][column2]))
				|| (map.get(labels[row3][column3]) && !labelSet
						.contains(labels[row3][column3]))
				|| (map.get(labels[row4][column4]) && !labelSet
						.contains(labels[row4][column4]))) {
			score += 10;
			checkFinishedRow();
			checkLineAmount();
			nextShape();
		}
	}

	private void checkLineAmount() {
		if (this.lines == 10) {
			moveUpLevel();
		}

	}

	private void moveUpLevel() {
		this.level++;
	//	this.lines = 0;
		this.speed += 15; 

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
					&& (!map.get(labels[row1 + 1][column1]) || labelSet
							.contains(labels[row1 + 1][column1]))
					&& (!map.get(labels[row2 + 1][column2]) || labelSet
							.contains(labels[row2 + 1][column2]))
					&& (!map.get(labels[row3 + 1][column3]) || labelSet
							.contains(labels[row3 + 1][column3]))
					&& (!map.get(labels[row4 + 1][column4]) || labelSet
							.contains(labels[row4 + 1][column4]))) {
				this.pieceShape.moveDown();
				refreshRowValues();
				this.numCells++;
			}
		}
		score += (2 * numCells);
	}

	

}
