package glazer.englard.tetris;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TetrisFrame extends JFrame implements KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Container container;
	private JPanel gridPanel;
	private JButton start;
	private JButton moveRight;
	private JButton moveLeft;
	private JButton moveDown;
	private JLabel[][] labels;
	private boolean isRunning;
	private int row;
	private int column;
	private JLabel gameOver;
	private HashMap<JLabel, Boolean> map;
	private ScheduledExecutorService executor;
	private String shape;
	private JPanel northPanel;
	private int coloredRow;
	private int coloredColumn;
	private int numCols;
	private int numRows;
	private Piece pieceShape;
	private Color color;
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

	public TetrisFrame() {
		setSize(370, 620);
		setTitle("Tetris");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.container = getContentPane();
		this.gridPanel = new JPanel();
		this.start = new JButton("START");
		this.moveLeft = new JButton("LEFT");
		this.moveRight = new JButton("RIGHT");
		this.moveDown = new JButton("DOWN");
		this.northPanel = new JPanel();
		this.labels = new JLabel[30][10];
		this.row = 0;
		this.column = 5;
		this.map = new HashMap<JLabel, Boolean>();
		this.gameOver = new JLabel();
		this.isRunning = true;
		this.shape = chooseShape();
		this.numCols = 10;
		this.numRows = 30;
		this.pieceShape = new IPiece();
		this.color = pieceShape.getColor();
		color = pieceShape.getColor();
		row1 = pieceShape.getRow1();
		column1 = pieceShape.getColumn1();
		row2 = pieceShape.getRow2();
		column2 = pieceShape.getColumn2();
		row3 = pieceShape.getRow3();
		column3 = pieceShape.getColumn3();
		row4 = pieceShape.getRow4();
		column4 = pieceShape.getColumn4();
		setComponents();
		addComponents();
	}

	public void setComponents() {
		this.container.setLayout(new BorderLayout());
		this.gridPanel.setLayout(new GridLayout(30, 10));
		this.northPanel.setLayout(new FlowLayout());
	}

	public void addComponents() {

		this.container.add(this.gridPanel, BorderLayout.CENTER);
		this.northPanel.add(start);
		this.northPanel.add(this.moveLeft);
		this.northPanel.add(this.moveRight);
		this.northPanel.add(this.moveDown);
		this.container.add(this.northPanel, BorderLayout.NORTH);
		this.container.add(this.gameOver, BorderLayout.SOUTH);
		this.container.addKeyListener(this);
		this.container.setFocusable(true);
		gridPanel.setFocusable(true);
		for (int i = 0; i < numRows; i++) {
			for (int x = 0; x < numCols; x++) {
				this.labels[i][x] = new JLabel();
				gridPanel.add(this.labels[i][x]);
				labels[i][x].setBorder(BorderFactory
						.createLineBorder(Color.GRAY));
			}
		}
		this.start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (int i = 0; i < numRows; i++) {
					for (int x = 0; x < numCols; x++) {
						labels[i][x].setBackground(Color.LIGHT_GRAY);
						labels[i][x].setOpaque(true);
						// String location = i + "," + x;
						// map.put(location, false);
						map.put(labels[i][x], false);
					}
				}
				start.setText("NEW GAME");

				gameLoop();
			}
		});
		this.moveLeft.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (column1 != 0 && column2 != 0 && column3 != 0
						&& column4 != 0 && !map.get(labels[row1][column1])
						&& !map.get(labels[row2][column2])
						&& !map.get(labels[row3][column3])
						&& !map.get(labels[row4][column4])) {
					column1--;
					column2--;
					column3--;
					column4--;
				}
				// if (column != 0) {
				// column--;
				// }
			}
		});
		this.moveRight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (column1 != numCols - 1 && column2 != numCols - 1
						&& column3 != numCols - 1 && column4 != numCols - 1
						&& !map.get(labels[row1][column1])
						&& !map.get(labels[row2][column2])
						&& !map.get(labels[row3][column3])
						&& !map.get(labels[row4][column4])) {
					column1++;
					column2++;
					column3++;
					column4++;
				}
				// if (column != numCols - 1) {
				// column++;
				// }
			}
		});
		this.moveDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("down");
				if (row1 + 2 < numRows && row2 + 2 < numRows
						&& row3 + 2 < numRows && row4 + 2 < numRows) {
					row1 = row1 + 2;
					row2 = row2 + 2;
					row3 = row3 + 2;
					row4 = row4 + 2;
				}
			}
		});
	}

	public void gameLoop() {
		this.executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(gameRunnable, 0, 1, TimeUnit.SECONDS);
	}

	Runnable gameRunnable = new Runnable() {

		public void run() // this becomes the loop
		{
			// row++;
			for (int i = 0; i < numCols; i++) {
				if (map.get(labels[0][i]) && map.get(labels[1][i])) {
					gameOver();
					executor.shutdown();
				}
			}
			if (row1 != 0) {
				// labels[row - 1][column].setBackground(Color.LIGHT_GRAY);
				labels[coloredRow1][coloredColumn1]
						.setBackground(Color.LIGHT_GRAY);
				map.put(labels[coloredRow1][coloredColumn1], false);
			}
			if (row2 != 0) {
				labels[coloredRow2][coloredColumn2]
						.setBackground(Color.LIGHT_GRAY);
				map.put(labels[coloredRow2][coloredColumn2], false);
			}
			if (row3 != 0) {
				labels[coloredRow3][coloredColumn3]
						.setBackground(Color.LIGHT_GRAY);
				map.put(labels[coloredRow3][coloredColumn3], false);
			}
			if (row4 != 0) {
				labels[coloredRow4][coloredColumn4]
						.setBackground(Color.LIGHT_GRAY);
				map.put(labels[coloredRow4][coloredColumn4], false);
			}
			// paintPiece();

			labels[row1][column1].setBackground(color);
			labels[row2][column2].setBackground(color);
			labels[row3][column3].setBackground(color);
			labels[row4][column4].setBackground(color);
			labels[row1][column1].setOpaque(true);
			labels[row2][column2].setOpaque(true);
			labels[row3][column3].setOpaque(true);
			labels[row4][column4].setOpaque(true);
			// labels[row][column].setBackground(pieceShape.getColor());
			// labels[row][column].setOpaque(true);
			// coloredRow = row;
			// coloredColumn = column;
			coloredRow1 = row1;
			coloredRow2 = row2;
			coloredRow3 = row3;
			coloredRow4 = row4;
			coloredColumn1 = column1;
			coloredColumn2 = column2;
			coloredColumn3 = column3;
			coloredColumn4 = column4;
			repaint();

			// map.put(labels[row][column], true);
			map.put(labels[row1][column1], true);
			map.put(labels[row2][column2], true);
			map.put(labels[row3][column3], true);
			map.put(labels[row4][column4], true);
			row1++;
			row2++;
			row3++;
			row4++;

			if (map.get(labels[row1][column1])
					|| map.get(labels[row2][column2])
					|| map.get(labels[row3][column3])
					|| map.get(labels[row4][column4]) || row1 == 28
					|| row2 == 28 || row3 == 28 || row4 == 28) {
				checkFinishedRow();
				// row = 0;
				// column = numCols / 2;

				// chooseShape();
				color = pieceShape.getColor();
				row1 = pieceShape.getRow1();
				column1 = pieceShape.getColumn1();
				row2 = pieceShape.getRow2();
				column2 = pieceShape.getColumn2();
				row3 = pieceShape.getRow3();
				column3 = pieceShape.getColumn3();
				row4 = pieceShape.getRow4();
				column4 = pieceShape.getColumn4();
				System.out.println(row1 + ", " + column1);
			}
			// System.out.println(row);

			// System.out.println("Still running");
		}

	};

	public void checkFinishedRow() {
		boolean fullLine = true;
		int checkCol;
		for (int i = 0; i < numRows; i++) {
			checkCol = 0;
			fullLine = true;
			while (fullLine) {
				// String box = i + "," + checkCol;
				// if (!map.get(box)) {
				if (!map.get(labels[i][checkCol])) {
					fullLine = false;
				} else {
					if (checkCol == 9) {
						for (int x = 0; x < numCols; x++) {
							labels[i][x].setBackground(Color.LIGHT_GRAY);
							// String peice = i + "," + x;
							// map.put(peice, false);
							map.put(labels[i][x], false);
						}
						moveDown(i);
						repaint();
						fullLine = false;
					}

					checkCol++;

				}

			}
		}
	}

	/**
	 * Moves everything above this row down a row
	 * 
	 * @param column
	 */
	private void moveDown(int cRow) {
		// TODO Auto-generated method stub
		// String peice;
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

	private String chooseShape() {
		Random newShape = new Random();
		int numShape = newShape.nextInt(5);
		switch (numShape) {
		case 0:
			shape = "L";
			break;
		case 1:
			shape = "Z";
			break;
		case 2:
			shape = "S";
			break;
		case 3:
			shape = "l";
			this.pieceShape = new IPiece();
			break;
		case 4:
			shape = "T";
			break;
		case 5:
			shape = "O";
			break;
		case 6:
			shape = "J";
			break;
		}
		return this.shape;
	}

	public void gameOver() {
		this.gameOver.setText("GAME OVER");
	}

	public void keyPressed(KeyEvent e) {
		System.out.println("pressed");
		int c = e.getKeyCode();
		if (c == KeyEvent.VK_UP) {
			// b.y--;
		} else if (c == KeyEvent.VK_DOWN) {
			// b.y++;
		} else if (c == KeyEvent.VK_LEFT) {
			System.out.println("left");
			if (column != 0) {
				column--;
			}
		} else if (c == KeyEvent.VK_RIGHT) {
			System.out.println("right");
			if (column != 9) {
				column++;
			}
		}
		// System.out.println (b.x);
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("released");
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("typed");
	}
}
