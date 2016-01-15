package gameframework.assets;

import java.io.BufferedInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Class allowing to create sounds and play them. It is not intended to play musics.
 */
public class Sound {
	
	/**
	 * The sound clip instace
	 */
	private Clip clip;

	/**
	 * True if the sound must loop, false otherwise
	 */
	private boolean isLooping;

	/**
	 * Creates and loads the sound
	 * @param path the path to the sound asset
	 * @throws IOException 
	 * @throws UnsupportedAudioFileException 
	 * @throws LineUnavailableException 
	 */
	public Sound(String path) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		BufferedInputStream bis = new BufferedInputStream(getClass().getResourceAsStream(path));
		AudioInputStream inputStream;
		inputStream = AudioSystem.getAudioInputStream(bis);
		
		AudioFormat format = inputStream.getFormat();
		DataLine.Info info = new DataLine.Info(Clip.class, format);
		clip = (Clip) AudioSystem.getLine(info);
		clip.open(inputStream);
		
		isLooping = false;
	}

	/**
	 * Determines if the sound is currently playing.
	 * @return true if the sound is playing, false otherwise
	 */
	public boolean isPlaying() {
		return clip.isRunning();
	}
	
	/**
	 * @return true if the sound must loop, false otherwise
	 */
	public boolean isLooping() {
		return isLooping;
	}
	
	/**
	 * @param isLooping true if the sound must loop, false otherwise
	 */
	public void setLooping(boolean isLooping) {
		this.isLooping = isLooping;
	}

	/**
	 * Plays the sound. If you play a sound that is already playing, it will
	 * start again from the beginning.
	 */
	public void play() {
		clip.setFramePosition(0);
		
		int loopCount = this.isLooping() ? Clip.LOOP_CONTINUOUSLY : 0;
		clip.loop(loopCount);
	}
	
}

