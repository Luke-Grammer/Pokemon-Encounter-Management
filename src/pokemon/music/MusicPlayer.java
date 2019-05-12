package pokemon.music;

import java.io.File;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;

public class MusicPlayer extends Thread
{
	private static final String TRACK_NAME = "P_GO.mp3";
	private MediaPlayer mediaPlayer = null;
	
	public MusicPlayer()
	{
		@SuppressWarnings("unused")
		JFXPanel fxPanel = new JFXPanel();
		File f = new File("Resources");
		if (!f.exists() || !f.canRead())
		{
			System.out.println("Warning:\n\t" + f.getAbsolutePath() + " does not exist for reading!");
		}
	}
	
	public void run()
	{
		try 
		{
			Media track = new Media(new File("Resources" + File.separatorChar + TRACK_NAME).toURI().toString());
			mediaPlayer = new MediaPlayer(track);
			mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
			mediaPlayer.play();		
		} catch(MediaException e) 
		{
			System.out.println("Could not find " + new File("Resources" + File.separatorChar + TRACK_NAME).getAbsolutePath() + "!");
		}
	}
}
