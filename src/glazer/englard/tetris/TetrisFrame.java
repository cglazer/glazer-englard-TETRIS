package glazer.englard.tetris;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
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
	private JLabel levelLabel;
	private JLabel level;
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
	private JPanel westNorthPanel;
	private JPanel westSouthPanel;
	private ImageIcon tetrisIcon;
	private JLabel tetrisIconLabel;

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
		this.levelLabel = new JLabel("Level");
		this.level = new JLabel("1");
		this.executor = Executors.newScheduledThreadPool(1);
		this.executor2 = Executors.newScheduledThreadPool(1);
		this.eastPanel = new JPanel();
		this.nextPieceLabels = new JLabel[2][4];
		this.eastHolder = new JPanel();
		this.isPaused = false;
		this.menuBar = new DropMenu();
		this.westNorthPanel = new JPanel();
		this.westSouthPanel = new JPanel();
		this.tetrisIcon = new ImageIcon(this.getClass().getResource(
				"tetrisIcon.jpg"));
		this.tetrisIconLabel = new JLabel();
		Image img = tetrisIcon.getImage();
		Image newimg = img.getScaledInstance(160, 100,
				java.awt.Image.SCALE_SMOOTH);
		this.tetrisIcon = new ImageIcon(newimg);

		setComponents();
		addComponents();
	}

	public void setComponents() {
		this.container.setLayout(new BorderLayout());
		this.northPanel.setLayout(new FlowLayout());
		// this.westPanel.setLayout(new BoxLayout(this.westPanel,
		// BoxLayout.PAGE_AXIS));
		// this.eastHolder.setLayout(new BoxLayout(this.eastHolder,
		// BoxLayout.PAGE_AXIS));
		this.container.setFocusable(true);
		this.eastHolder.setLayout(new BorderLayout());
		this.eastPanel.setLayout(new GridLayout(3, 4));

		this.eastPanel.setMinimumSize(new Dimension(150, 100));
		this.eastPanel.setPreferredSize(new Dimension(150, 100));
		this.eastPanel.setMaximumSize(new Dimension(150, 100));
		this.eastHolder.setBackground(Color.BLUE);
		this.northPanel.setBackground(Color.BLUE);
		this.westPanel.setBackground(Color.BLUE);
		this.eastPanel.setBackground(Color.BLUE);
		this.westNorthPanel.setBackground(Color.BLUE);
		this.westSouthPanel.setBackground(Color.BLUE);
		this.score.setMinimumSize(new Dimension(130, 50));
		this.score.setPreferredSize(new Dimension(130, 50));
		this.score.setMaximumSize(new Dimension(130, 50));
		// this.score.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.lines.setMinimumSize(new Dimension(130, 50));
		this.lines.setPreferredSize(new Dimension(130, 50));
		this.lines.setMaximumSize(new Dimension(130, 50));
		
		this.level.setMinimumSize(new Dimension(130, 50));
		this.level.setPreferredSize(new Dimension(130, 50));
		this.level.setMaximumSize(new Dimension(130, 50));
		Border paddingBorder = BorderFactory.createEmptyBorder(0, 0, 0, 0);
		Border border = BorderFactory.createLineBorder(Color.WHITE);
		Border compound = BorderFactory.createCompoundBorder(paddingBorder,
				border);
		this.lines.setBorder(compound);
		this.score.setBorder(compound);
		this.level.setBorder(compound);
		this.eastPanel.setBorder(BorderFactory.createCompoundBorder(
				paddingBorder, border));
		lines.setHorizontalAlignment(SwingConstants.CENTER);
		lines.setVerticalAlignment(SwingConstants.CENTER);
		score.setHorizontalAlignment(SwingConstants.CENTER);
		score.setVerticalAlignment(SwingConstants.CENTER);
		level.setHorizontalAlignment(SwingConstants.CENTER);
		level.setVerticalAlignment(SwingConstants.CENTER);
		lines.setFont(new Font("Serif", Font.PLAIN, 20));
		this.linesLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		this.score.setFont(new Font("Serif", Font.PLAIN, 20));
		this.scoreLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		level.setFont(new Font("Serif", Font.PLAIN, 20));
		this.levelLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		this.westPanel.setLayout(new BorderLayout());
		this.westPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 50, 5));
		this.eastHolder.setBorder(BorderFactory.createEmptyBorder(5, 5, 50, 5));
		this.tetrisIconLabel.setBorder(border);
		this.lines.setForeground(Color.WHITE);
		this.lines.setBackground(Color.BLACK);
		this.lines.setOpaque(true);
		this.score.setForeground(Color.WHITE);
		this.score.setBackground(Color.BLACK);
		this.score.setOpaque(true);
		this.level.setForeground(Color.WHITE);
		this.level.setBackground(Color.BLACK);
		this.level.setOpaque(true);
		this.eastPanel.setBackground(Color.BLACK);
		this.eastPanel.setOpaque(true);
		this.westPanel.setMinimumSize(new Dimension(170, 500));
		this.westPanel.setPreferredSize(new Dimension(170, 500));
		this.westPanel.setMaximumSize(new Dimension(170, 500));
		this.eastHolder.setMinimumSize(new Dimension(170, 500));
		this.eastHolder.setPreferredSize(new Dimension(170, 500));
		this.eastHolder.setMaximumSize(new Dimension(170, 500));

		this.tetrisIconLabel.setIcon(this.tetrisIcon);
		// this.nextShape = grid.getNextShape();
		// setEastPanel();
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
		this.eastHolder.add(this.westNorthPanel, BorderLayout.CENTER);
		this.container.add(this.eastHolder, BorderLayout.EAST);
		this.westNorthPanel.add(this.scoreLabel);
		this.westNorthPanel.add(this.score);
		this.westNorthPanel.add(this.levelLabel);
		this.westNorthPanel.add(this.level);
		this.westSouthPanel.add(this.linesLabel);
		this.westSouthPanel.add(this.lines);
		this.westPanel.add(this.tetrisIconLabel, BorderLayout.NORTH);
		this.westPanel.add(this.westSouthPanel, BorderLayout.CENTER);
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
				// this.nextPieceLabels[i][x].setBorder(BorderFactory
				// .createLineBorder(Color.BLACK));
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
		setEastPanel();
		executor.scheduleAtFixedRate(gameRunnable, 0, grid.getSpeed(),
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
				runningThread = new RunningThread(grid, score, lines,level);
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
				this.nextPieceLabels[i][x].setBackground(Color.BLACK);
				this.nextPieceLabels[i][x].setOpaque(true);
				this.nextPieceLabels[i][x].setBorder(BorderFactory
						.createLineBorder(Color.BLACK));
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
				.createLineBorder(Color.DARK_GRAY));
		this.nextPieceLabels[row2][column2].setBorder(BorderFactory
				.createLineBorder(Color.DARK_GRAY));
		this.nextPieceLabels[row3][column3].setBorder(BorderFactory
				.createLineBorder(Color.DARK_GRAY));
		this.nextPieceLabels[row4][column4].setBorder(BorderFactory
				.createLineBorder(Color.DARK_GRAY));
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
		} else if (c == KeyEvent.VK_SPACE) {
			// fall down
			this.grid.fallDown();
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