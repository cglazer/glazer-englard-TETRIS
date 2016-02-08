package glazer.englard.tetris;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GridPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int row;
	private int column;

	public GridPanel() {
		setLayout(new GridLayout(3, 3));
		JLabel[][] labels = new JLabel[3][3];
		this.row = 0;
		this.column = 0;
		for (int i = 0; i < 3; i++) {
			for (int x = 0; x < 3; x++) {
				labels[i][x] = new JLabel();

				labels[i][x].setOpaque(true);
				add(labels[i][x]);
				labels[i][x].setBorder(BorderFactory
						.createLineBorder(Color.GRAY));
			}
		}
		labels[row][column].setBackground(Color.YELLOW);
		repaint();
	}
	public void clicked(int column){
		System.out.println("clicked " +column);
	}
	
	
}
