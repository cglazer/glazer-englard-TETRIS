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
	private JPanel panel;
	private Container container;

	public TestingClass() {
		setSize(800, 600);
		setTitle("testing");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		panel = new JPanel();
		panel.setLayout(new GridLayout(3, 3));
		addKeyListener(this);
		setFocusable(true);
		Container container = getContentPane();
		container.add(panel, BorderLayout.CENTER);
		this.labels = new JLabel[3][3];
		this.row = 0;
		this.column = 0;
		for (int i = 0; i < 3; i++) {
			for (int x = 0; x < 3; x++) {
				labels[i][x] = new JLabel();

				labels[i][x].setOpaque(true);
				panel.add(labels[i][x]);
				labels[i][x].setBorder(BorderFactory
						.createLineBorder(Color.GRAY));
			}
		}
		labels[row][column].setBackground(Color.YELLOW);
		this.start = new JButton("START");
		container.add(start, BorderLayout.NORTH);
		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				while (row < 3) {

					labels[row][column].setBackground(Color.YELLOW);
					row++;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		});

	}

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
			if (column != 0) {
				labels[row][column].setBackground(Color.LIGHT_GRAY);
				column--;
				labels[row][column].setBackground(Color.YELLOW);

				repaint();
			}
		} else if (c == KeyEvent.VK_RIGHT) {
			System.out.println("right");

			if (column != 2) {
				this.labels[row][column].setBackground(Color.LIGHT_GRAY);
				// this.color = Color.BLUE;
				column++;
				labels[row][column].setBackground(Color.YELLOW);

				repaint();
			}
		}

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
