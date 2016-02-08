package glazer.englard.tetris;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TestingClass extends JFrame implements KeyListener {
	private JLabel[][] labels;
	private int row;
	private int column;
	private JButton start;
	private GridPanel panel;
	private Container container;

	public TestingClass() {
		setSize(800, 600);
		setTitle("testing");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		panel = new GridPanel();
		
		addKeyListener(this);
		setFocusable(true);
		Container container=getContentPane();
		container.add(panel, BorderLayout.CENTER);
		
		this.start = new JButton("START");
		container.add(start, BorderLayout.NORTH);
		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				ScheduledExecutorService executor = Executors
						.newScheduledThreadPool(1);
				executor.scheduleAtFixedRate(gameRunnable, 0, 1,
						TimeUnit.SECONDS);

			}

		});
		
	}

	Runnable gameRunnable = new Runnable() {
		public void run() {
			System.out.println("hello " + column);
			panel.clicked(column);
		}
	};

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("Here");
		int c = e.getKeyCode();
		if (c == KeyEvent.VK_UP) {
			// b.y--;
		} else if (c == KeyEvent.VK_DOWN) {
			// b.y++;
		} else if (c == KeyEvent.VK_LEFT) {
			System.out.println("left");
			// this.color = Color.GREEN;
			labels[row][column].setBackground(Color.LIGHT_GRAY);
			if (column != 0) {
				column--;
				labels[row][column].setBackground(Color.YELLOW);
			}
			repaint();

		} else if (c == KeyEvent.VK_RIGHT) {
			System.out.println("right");
			labels[row][column].setBackground(Color.LIGHT_GRAY);
			if (column != 2) {
				// this.color = Color.BLUE;
				column++;
				labels[row][column].setBackground(Color.YELLOW);
			}
			repaint();
		}

		repaint();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
System.out.println("Here");
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Here");

	}

	public static void main(String[] args) {
		TestingClass frame = new TestingClass();
		frame.setVisible(true);
	}

}
