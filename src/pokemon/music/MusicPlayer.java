package pokemon.music;

import java.io.File;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;

public class MusicPlayer extends Thread
{
	private static final String TRACK_NAME = "P_GO.mp3";
	public static final double STARTING_VOLUME = 0.5;
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
		mediaPlayer.setVolume(0.5);
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
	
	public void setVolume(double volume)
	{
		mediaPlayer.setVolume(volume);
	}
}
