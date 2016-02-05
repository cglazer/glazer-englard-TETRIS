package glazer.englard.tetris;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
	private JPanel northPanel;
	private TetrisGrid grid;
	private JLabel scoreLabel;
	private JLabel score;
	private JLabel lines;
	private JLabel linesLabel;
	private JPanel westPanel;

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
		setComponents();
		addComponents();
	}

	public void setComponents() {
		this.container.setLayout(new BorderLayout());
		this.northPanel.setLayout(new FlowLayout());
		this.westPanel.setLayout(new BoxLayout(this.westPanel,
				BoxLayout.PAGE_AXIS));
		this.container.setFocusable(true);
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
		this.westPanel.add(this.scoreLabel);
		this.westPanel.add(this.score);
		this.westPanel.add(this.linesLabel);
		this.westPanel.add(this.lines);
		this.container.addKeyListener(this);

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
		grid.startGame();
		this.executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(gameRunnable, 0, 200,
				TimeUnit.MILLISECONDS);
	}

	Runnable gameRunnable = new Runnable() {

		public void run() // this becomes the loop
		{
			if (grid.checkGameOver()) {
				gameOver();
			} else {
				grid.runGame();
				score.setText(String.valueOf(grid.getScore()));
				lines.setText(String.valueOf(grid.getLines()));
			}
		}
	};

	public void gameOver() {
		this.gameOver.setText("GAME OVER");
		executor.shutdown();

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