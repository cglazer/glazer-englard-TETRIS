package glazer.englard.tetris;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

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
	private JPanel eastHolder;
	private boolean isPaused;
	private DropMenu menuBar;
	private JFrame frame;
	public TetrisFrame() {
		setSize(640, 650);
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
		this.eastHolder = new JPanel();
		this.isPaused = false;
		this.menuBar = new DropMenu();
		this.frame=this;
		setComponents();
		addComponents();
	}

	public void setComponents() {
		this.container.setLayout(new BorderLayout());
		this.northPanel.setLayout(new FlowLayout());
		this.westPanel.setLayout(new BoxLayout(this.westPanel,
				BoxLayout.PAGE_AXIS));
		this.container.setFocusable(true);
		this.eastHolder.setLayout(new BorderLayout());
		this.eastPanel.setLayout(new GridLayout(3, 4));
		this.eastPanel.setPreferredSize(new Dimension(90, 70));

		this.eastHolder.setBackground(Color.BLUE);
		this.northPanel.setBackground(Color.BLUE);
		this.westPanel.setBackground(Color.BLUE);
this.eastPanel.setBackground(Color.BLUE);
this.score.setMinimumSize(new Dimension(100, 50));
this.score.setPreferredSize(new Dimension(100, 50));
this.score.setMaximumSize(new Dimension(100, 50));
this.score.setBorder(BorderFactory.createLineBorder(Color.BLACK));
this.linesLabel.setMinimumSize(new Dimension(100, 50));
this.linesLabel.setPreferredSize(new Dimension(100, 50));
this.linesLabel.setMaximumSize(new Dimension(100, 50));
Border paddingBorder = BorderFactory.createEmptyBorder(20,40,20,40);
//this.linesLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
Border border = BorderFactory.createLineBorder(Color.BLACK);

this.linesLabel.setBorder(BorderFactory.createCompoundBorder(border,paddingBorder));

//this.linesLabel.set
this.westPanel.setMinimumSize(new Dimension(170, 500));
this.westPanel.setPreferredSize(new Dimension(170, 500));
this.westPanel.setMaximumSize(new Dimension(170, 500));
this.eastHolder.setMinimumSize(new Dimension(170, 500));
this.eastHolder.setPreferredSize(new Dimension(170, 500));
this.eastHolder.setMaximumSize(new Dimension(170, 500));
this.eastPanel.setMinimumSize(new Dimension(100, 80));
this.eastPanel.setPreferredSize(new Dimension(100, 80));
this.eastPanel.setMaximumSize(new Dimension(100, 80));
//JLabel back= new JLabel();
//ImageIcon icon= new ImageIcon(("unnamed.gif"));
//back.setIcon(icon);

//frame.setContentPane(back);
	}

	public void addComponents() {

		this.container.add(this.grid, BorderLayout.CENTER);
		this.northPanel.add(start);
		// this.northPanel.add(this.turn);
		// this.northPanel.add(this.moveLeft);
		// this.northPanel.add(this.moveRight);
		// this.northPanel.add(this.moveDown);

		this.setMenuBar(menuBar);
		this.container.add(this.northPanel, BorderLayout.NORTH);
		this.container.add(this.gameOver, BorderLayout.SOUTH);
		this.container.add(this.westPanel, BorderLayout.WEST);
		this.eastHolder.add(eastPanel, BorderLayout.NORTH);
		this.container.add(this.eastHolder, BorderLayout.EAST);
		this.westPanel.add(this.scoreLabel);
		this.westPanel.add(this.score);
		this.westPanel.add(this.linesLabel);
		this.westPanel.add(this.lines);

		this.container.addKeyListener(this);
		// add space before to line things up
		this.eastPanel.add(new JLabel(" "));
		this.eastPanel.add(new JLabel(" "));
		this.eastPanel.add(new JLabel(" "));
		this.eastPanel.add(new JLabel(" "));
		for (int i = 0; i < 2; i++) {
			for (int x = 0; x < 4; x++) {
				this.nextPieceLabels[i][x] = new JLabel(" ");
				this.eastPanel.add(this.nextPieceLabels[i][x]);
				//this.nextPieceLabels[i][x].setBorder(BorderFactory
				//		.createLineBorder(Color.BLACK));
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
				this.nextPieceLabels[i][x].setBackground(Color.BLUE);
				this.nextPieceLabels[i][x].setOpaque(true);

			}
		}
	}

	Runnable playSound = new Runnable() {

		public void run() {
			// MusicThread musicThread= new MusicThread();
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
			} else if (isPaused) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				runningThread = new RunningThread(grid, score, lines);
				runningThread.start();
				setEastPanel();
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
				this.nextPieceLabels[i][x].setBackground(Color.BLUE);
				this.nextPieceLabels[i][x].setOpaque(true);
				this.nextPieceLabels[i][x].setBorder(BorderFactory
							.createLineBorder(Color.BLUE));
			}
		}
		this.nextShape = grid.getNextShape();
		this.nextShapeColor = this.nextShape.getColor();
		this.column1 = this.nextShape.getColumn1() - 3;
		this.column2 = this.nextShape.getColumn2() - 3;
		this.column3 = this.nextShape.getColumn3() - 3;
		this.column4 = this.nextShape.getColumn4() - 3;
		this.row1 = this.nextShape.getRow1() + 1;
		this.row2 = this.nextShape.getRow2() + 1;
		this.row3 = this.nextShape.getRow3() + 1;
		this.row4 = this.nextShape.getRow4() + 1;
		this.nextPieceLabels[row1][column1].setBackground(this.nextShapeColor);
		this.nextPieceLabels[row2][column2].setBackground(this.nextShapeColor);
		this.nextPieceLabels[row3][column3].setBackground(this.nextShapeColor);
		this.nextPieceLabels[row4][column4].setBackground(this.nextShapeColor);
		this.nextPieceLabels[row1][column1].setBorder(BorderFactory
						.createLineBorder(Color.BLACK));
		this.nextPieceLabels[row2][column2].setBorder(BorderFactory
				.createLineBorder(Color.BLACK));
		this.nextPieceLabels[row3][column3].setBorder(BorderFactory
				.createLineBorder(Color.BLACK));
		this.nextPieceLabels[row4][column4].setBorder(BorderFactory
				.createLineBorder(Color.BLACK));
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
		} else if (c == KeyEvent.VK_P) {
			// pause
			this.isPaused = true;
		} else if (c == KeyEvent.VK_R) {
			// resume
			this.isPaused = false;
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