package glazer.englard.tetris;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DropMenu extends MenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Menu properites;
	private MenuItem rules;

	public DropMenu() {

		this.properites = new Menu("Properties");
		this.add(properites);
		this.rules = new MenuItem("Rules");
		this.properites.add(rules);

		rules.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				RulesFrame frame = new RulesFrame();
				frame.setVisible(true);

			}

		});
	}

}
