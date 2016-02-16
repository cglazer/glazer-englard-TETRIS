package glazer.englard.tetris;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

public class MusicThread extends Thread{
	private AudioClip click;
public MusicThread(){
	
}
	public void run(){
		//Applet.newAudioClip(getClass().getResource("jolly-game-groove.wav"))
	//	.play();
		URL urlClick = getClass().getResource("jolly-game-groove.wav");
		click = Applet.newAudioClip(urlClick);
		click.play();
}
	
	public void stopMusic() {
		click.stop();
	}
}
