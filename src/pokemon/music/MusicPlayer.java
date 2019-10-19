package pokemon.music;

import java.io.File;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.*;

public class MusicPlayer extends Thread
{
	private static final String TRACK_NAME = "P_GO.mp3";
	public static final int STARTING_VOLUME = 5;
	private MediaPlayer mediaPlayer = null;
	
	public MusicPlayer()
	{
		@SuppressWarnings("unused")
		JFXPanel fxPanel = new JFXPanel();
		
		try
		{
			Media track = new Media(new File("Resources" + File.separatorChar + TRACK_NAME).toURI().toString());
			mediaPlayer = new MediaPlayer(track);
		} catch(MediaException e) 
		{
			System.out.println("Warning: Could not find " + new File("Resources" + File.separatorChar + TRACK_NAME).getAbsolutePath() + "!");
		}
	}
	
	public void run()
	{
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer.setVolume(STARTING_VOLUME / 10.0);
		mediaPlayer.play();		
	}
	
	public void pause()
	{
		mediaPlayer.pause();		
	}
	
	public void unpause()
	{
		mediaPlayer.play();		
	}
	
	public void setVolume(int volume)
	{
		mediaPlayer.setVolume(volume / 10.0);
	}
}
