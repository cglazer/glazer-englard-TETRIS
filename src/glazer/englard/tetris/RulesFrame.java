package glazer.englard.tetris;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class RulesFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RulesFrame() {
		setSize(300, 325);
		setTitle("Tetris Rules");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JLabel list = new JLabel(
				"<html><p style=\" padding:10;\">To play press the start button<br> "
						+ "To PAUSE press p<br>To RESUME press r<br> "
						+ "To TURN the piece use the up arrow <br>To MOVE RIGHT hit the right arrow <br>"
						+ "To MOVE LEFT hit the left arrow <br>"
						+ "To DROP the piece press the spacebar<br> Scoring: <br> 1 Line = 40*(level)<br> 2 Lines = 100*(level) <br>3 Lines = 300*(level) <br> 4 Lines = 1200*(level)"
						+ "<br>Clear the board = 2000*(level)"
						+ "<br>Every piece = 10*(level) <br> drop = 2 pionts per cell </p></html>");
		list.setFont(new Font("Serif", Font.PLAIN, 14));
		list.setForeground(Color.CYAN);
		Container container = this.getContentPane();
		container.setBackground(new Color(45, 45, 45));
		add(list);

	}
}
