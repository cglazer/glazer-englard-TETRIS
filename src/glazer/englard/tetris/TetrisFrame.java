package glazer.englard.tetris;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
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

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

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
	private JLabel[][] labels;
	private boolean isRunning;
	private int row;
	private int column;
	private String colored;
	private JLabel gameOver;
	private HashMap<String, Boolean> map;
	private ScheduledExecutorService executor;
	private Color color;
	private String shape;
	private JPanel northPanel;

	public TetrisFrame() {
		setSize(370, 620);
		setTitle("Tetris");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.container = getContentPane();
		this.gridPanel = new JPanel();
		this.start = new JButton("START");
		this.moveLeft= new JButton("MOVE LEFT");
		this.moveRight=new JButton("MOVE RIGHT");
		this.northPanel=new JPanel();
		this.labels = new JLabel[30][10];
		this.row = 0;
		this.column = 5;
		this.map = new HashMap<String, Boolean>();
		this.colored = "";
		this.gameOver = new JLabel();
		this.isRunning = true;
		this.color = chooseColor();
this.shape= chooseShape();
		setComponents();
		addComponents();
	}

	public void setComponents() {
		this.container.setLayout(new BorderLayout());
		this.gridPanel.setLayout(new GridLayout(30, 10));
	}

	public void addComponents() {

		this.container.add(this.gridPanel, BorderLayout.CENTER);
		this.northPanel.add(start);
		this.northPanel.add(this.moveLeft);
		this.northPanel.add(this.moveRight);
		this.container.add(this.northPanel, BorderLayout.NORTH);
		this.container.add(this.gameOver, BorderLayout.SOUTH);
		this.container.addKeyListener(this);
		this.container.setFocusable(true);
		gridPanel.setFocusable(true);
		for (int i = 0; i < 30; i++) {
			for (int x = 0; x < 10; x++) {
				this.labels[i][x] = new JLabel();
				gridPanel.add(this.labels[i][x]);
				labels[i][x].setBorder(BorderFactory
						.createLineBorder(Color.GRAY));
			}
		}
		this.start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (int i = 0; i < 30; i++) {
					for (int x = 0; x < 10; x++) {
						labels[i][x].setBackground(Color.LIGHT_GRAY);
						labels[i][x].setOpaque(true);
						String location = i + "," + x;
						map.put(location, false);
					}
				}
				start.setText("NEW GAME");

				gameLoop();
			}
		});
		this.moveLeft.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(column!=0){
			column--;
			}}
		});
		this.moveRight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			if(column!=9){
				column++;
			}}
		});
	}

	public void gameLoop() {
		this.executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(gameRunnable, 0, 1, TimeUnit.SECONDS);
	}

	Runnable gameRunnable = new Runnable() {

		public void run() // this becomes the loop
		{
			for (int i = 0; i < 10; i++) {
				String check = "0," + i;
				String checkNext = "1," + i;
				if (map.get(check) && map.get(checkNext)) {
					gameOver();
					executor.shutdown();
				}
			}
			if (row != 0) {
				labels[row - 1][column].setBackground(Color.LIGHT_GRAY);
				map.put(colored, false);
			}

			labels[row][column].setBackground(color);
			labels[row][column].setOpaque(true);
			repaint();
			colored = row + "," + column;
			map.put(colored, true);
			row++;
			String nextLocation = row + "," + column;
			if (map.get(nextLocation) || row == 10) {
				row = 0;
				chooseColor();
				chooseShape();
			}
			System.out.println(row);

			System.out.println("Still running");
		}

	};

	private Color chooseColor() {
		Random newColor = new Random();
		int num = newColor.nextInt(8);
		switch (num) {
		case 0:
			this.color = Color.BLUE;
			break;
		case 1:
			this.color = Color.CYAN;
			break;
		case 2:
			this.color = Color.GREEN;
			break;
		case 3:
			this.color = Color.MAGENTA;
			break;
		case 4:
			this.color = Color.ORANGE;
			break;
		case 5:
			this.color = Color.PINK;
		case 6:
			this.color = Color.RED;
			break;
		case 7:
			this.color = Color.YELLOW;
			break;
		}
		return this.color;
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
			shape="S";
			break;
		case 3:
			shape="l";
			break;
		case 4:
			shape= "T";
			break;
		case 5:
			shape="BOX";
			break;
		case 6:
			shape= "d";
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
			this.color = Color.GREEN;
			if (column != 0) {
				column--;
			}
		} else if (c == KeyEvent.VK_RIGHT) {
			System.out.println("right");
			if (column != 9) {
				this.color = Color.BLUE;
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
	/**
	 * private class MotionAction extends AbstractAction implements
	 * ActionListener { private int deltaX; private int deltaY;
	 * 
	 * public MotionAction(String name, int deltaX, int deltaY) { super(name);
	 * 
	 * this.deltaX = deltaX; this.deltaY = deltaY; }
	 * 
	 * public void actionPerformed(ActionEvent e) { move(deltaX, deltaY); }
	 * public MotionAction addAction(String name, int deltaX, int deltaY) {
	 * MotionAction action = new MotionAction(name, deltaX, deltaY);
	 * 
	 * KeyStroke pressedKeyStroke = KeyStroke.getKeyStroke(name); InputMap
	 * inputMap = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
	 * inputMap.put(pressedKeyStroke, name); component.getActionMap().put(name,
	 * action);
	 * 
	 * return action; } }
	 */
}
