package glazer.englard.tetris;

import java.applet.Applet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

public class Testing extends JFrame {
	private ScheduledExecutorService executor;
	private ScheduledExecutorService executor2;

	public static void main(String[] args) {
		Testing frame = new Testing();
		frame.setVisible(true);
	}

	public Testing() {
		setSize(800, 600);
		setTitle("Testing");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// playSound();
		this.executor=Executors.newScheduledThreadPool(1);
		this.executor.scheduleAtFixedRate(test, 0, 200, TimeUnit.MILLISECONDS);
		this.executor2 = Executors.newScheduledThreadPool(1);
		this.executor2.scheduleAtFixedRate(playSound, 0, 22, TimeUnit.SECONDS);
	}

	/**
	 * public void playSound() { new Thread(new Runnable() {
	 * 
	 * @Override public void run() {
	 *           Applet.newAudioClip(getClass().getResource("jolly-game-groove.wav"
	 *           )).play(); } }).start(); }
	 */
	Runnable playSound = new Runnable() {
		@Override
		public void run() {
			Applet.newAudioClip(getClass().getResource("jolly-game-groove.wav"))
					.play();
		}
	};
	Runnable test = new Runnable() {
		@Override
		public void run() {
			System.out.println("Hello");
		}
	};
}
