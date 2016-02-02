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
		
		repaint();
	}
	public void clicked(int column){
		System.out.println("clicked " +column);
	}
	
	
}
