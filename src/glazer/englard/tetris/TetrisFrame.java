package glazer.englard.tetris;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
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
	private JButton start;
	private JButton moveRight;
	private JButton moveLeft;
	private JButton moveDown;
	private JButton turn;
	private JLabel gameOver;
	private ScheduledExecutorService executor;
	private ScheduledExecutorService executor2;
	private JPanel northPanel;
	private TetrisGrid grid;
	private JLabel scoreLabel;
	private JLabel score;
	private JLabel lines;
	private JLabel linesLabel;
	private JPanel westPanel;
	private RunningThread runningThread;
	private JPanel eastPanel;
	private Piece nextShape;
	private JLabel[][] nextPieceLabels;
	private int row1;
	private int row2;
	private int row3;
	private int row4;
	private int column1;
	private int column2;
	private int column3;
	private int column4;
	private Color nextShapeColor;

	public TetrisFrame() {
		setSize(370, 620);
		setTitle("Tetris");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.container = getContentPane();
		this.start = new JButton("START");
		this.moveLeft = new JButton("LEFT");
		this.moveRight = new JButton("RIGHT");
		this.moveDown = new JButton("DOWN");
		this.turn = new JButton("TURN");
		this.northPanel = new JPanel();
		this.westPanel = new JPanel();
		this.gameOver = new JLabel();
		grid = new TetrisGrid();
		this.scoreLabel = new JLabel("Score");
		this.score = new JLabel("0");
		this.linesLabel = new JLabel("Lines");
		this.lines = new JLabel("0");
		this.executor = Executors.newScheduledThreadPool(1);
		this.executor2 = Executors.newScheduledThreadPool(1);
		this.eastPanel = new JPanel();
		this.nextPieceLabels = new JLabel[2][4];
		setComponents();
		addComponents();
	}

	public void setComponents() {
		this.container.setLayout(new BorderLayout());
		this.northPanel.setLayout(new FlowLayout());
		this.westPanel.setLayout(new BoxLayout(this.westPanel,
				BoxLayout.PAGE_AXIS));
		this.container.setFocusable(true);
		this.eastPanel.setLayout(new GridLayout(2, 4));
	}

	public void addComponents() {

		this.container.add(this.grid, BorderLayout.CENTER);
		this.northPanel.add(start);
		this.northPanel.add(this.turn);
		this.northPanel.add(this.moveLeft);
		this.northPanel.add(this.moveRight);
		this.northPanel.add(this.moveDown);
		this.container.add(this.northPanel, BorderLayout.NORTH);
		this.container.add(this.gameOver, BorderLayout.SOUTH);
		this.container.add(this.westPanel, BorderLayout.WEST);
		this.container.add(this.eastPanel, BorderLayout.EAST);
		this.westPanel.add(this.scoreLabel);
		this.westPanel.add(this.score);
		this.westPanel.add(this.linesLabel);
		this.westPanel.add(this.lines);
		this.container.addKeyListener(this);
		for (int i = 0; i < 2; i++) {
			for (int x = 0; x < 4; x++) {
				this.nextPieceLabels[i][x] = new JLabel("N");
				this.eastPanel.add(this.nextPieceLabels[i][x]);
				this.nextPieceLabels[i][x].setBorder(BorderFactory
						.createLineBorder(Color.BLACK));
			}
		}
		this.start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				start.setText("NEW GAME");
				start.setFocusable(false);
				moveLeft.setFocusable(false);
				moveRight.setFocusable(false);
				moveDown.setFocusable(false);
				turn.setFocusable(false);
				startGame();
			}
		});
		this.moveLeft.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				grid.moveLeft();
			}
		});

		this.moveRight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				grid.moveRight();
			}
		});

		this.moveDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				grid.moveDownFast();
				// System.out.println("down");

			}

		});
		this.turn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				grid.turn();

			}
		});

	}

	public void startGame() {
		// playSound();
		grid.startGame();
		executor.scheduleAtFixedRate(gameRunnable, 0, 200,
				TimeUnit.MILLISECONDS);
		// MusicThread musicThread= new MusicThread();
		// musicThread.start();
		this.executor2.scheduleAtFixedRate(playSound, 0, 22, TimeUnit.SECONDS);
		for (int i = 0; i < 2; i++) {
			for (int x = 0; x < 4; x++) {
				this.nextPieceLabels[i][x].setBackground(Color.LIGHT_GRAY);
				this.nextPieceLabels[i][x].setOpaque(true);

			}
		}
	}

	Runnable playSound = new Runnable() {

		public void run() {
			 //MusicThread musicThread= new MusicThread();
			// musicThread.start();

			Applet.newAudioClip(getClass().getResource("jolly-game-groove.wav"))
					.play();
		}
	};
	Runnable gameRunnable = new Runnable() {

		public void run() // this becomes the loop
		{
			if (grid.checkGameOver()) {
				gameOver();
			} else {
				runningThread = new RunningThread(grid, score, lines);
				runningThread.start();
				//setEastPanel();
			}
			/**
			 * if (grid.checkGameOver()) { gameOver(); } else { grid.runGame();
			 * score.setText(String.valueOf(grid.getScore()));
			 * lines.setText(String.valueOf(grid.getLines())); }
			 */
		}
	};

	public void gameOver() {
		this.gameOver.setText("GAME OVER");
		executor.shutdown();

	}

	public void setEastPanel() {
		for (int i = 0; i < 2; i++) {
			for (int x = 0; x < 4; x++) {
				this.nextPieceLabels[i][x].setBackground(Color.LIGHT_GRAY);
				this.nextPieceLabels[i][x].setOpaque(true);

			}
		}
		this.nextShape = grid.getNextShape();
		this.nextShapeColor=this.nextShape.getColor();
		this.column1 = this.nextShape.getColumn1() - 3;
		this.column2 = this.nextShape.getColumn2() - 3;
		this.column3 = this.nextShape.getColumn3() - 3;
		this.column4 = this.nextShape.getColumn4() - 3;
		this.row1 = this.nextShape.getRow1()+1;
		this.row2 = this.nextShape.getRow2()+1;
		this.row3 = this.nextShape.getRow3()+1;
		this.row4 = this.nextShape.getRow4()+1;
		this.nextPieceLabels[row1][column1].setBackground(this.nextShapeColor);
		this.nextPieceLabels[row2][column2].setBackground(this.nextShapeColor);
		this.nextPieceLabels[row3][column3].setBackground(this.nextShapeColor);
		this.nextPieceLabels[row4][column4].setBackground(this.nextShapeColor);
	}

	public void keyPressed(KeyEvent e) {
		int c = e.getKeyCode();
		if (c == KeyEvent.VK_UP) {
			grid.turn();
		} else if (c == KeyEvent.VK_DOWN) {
			grid.moveDownFast();
		} else if (c == KeyEvent.VK_LEFT) {
			grid.moveLeft();
		} else if (c == KeyEvent.VK_RIGHT) {
			grid.moveRight();
		}
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

}