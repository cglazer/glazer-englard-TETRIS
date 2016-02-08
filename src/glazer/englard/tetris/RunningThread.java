package glazer.englard.tetris;

import javax.swing.JLabel;

public class RunningThread extends Thread {
	private TetrisGrid grid;
	private JLabel score;
	private JLabel lines;

	public RunningThread(TetrisGrid grid, JLabel score, JLabel lines) {
		this.grid = grid;
		this.score = score;
		this.lines = lines;
	}

	@Override
	public void run() {
		grid.runGame();
		score.setText(String.valueOf(grid.getScore()));
		lines.setText(String.valueOf(grid.getLines()));
	}

}
