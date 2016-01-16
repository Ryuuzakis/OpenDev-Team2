package gameframework.assets;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Class allowing a user to manage sounds. This class will load all sounds
 * available and offers the possibility to access them with the filename of the sound.
 */
public class SoundManager {

	/**
	 * Path to the directory containing the sounds
	 */
	private final String SOUND_DIRECTORY;

	/**
	 * Map containing the sounds. The key is the filename of the sound,
	 * the value is the loaded sound.
	 */
	protected final Map<String, Sound> sounds;

	/**
	 * Constructor
	 */
	public SoundManager() {
		this("sounds");
	}

	/**
	 * Constructor
	 * @param path the path to the sounds directory
	 */
	public SoundManager(final String path) {
		SOUND_DIRECTORY = path;
		sounds = new HashMap<>();
		loadSounds();
	}

	/**
	 * Loads the sounds from the sound directory
	 */
	protected void loadSounds() {
		loadSounds(new File("/sounds"));
	}

	/**
	 * Loads the sounds from a specified directory.
	 * This method will also search the subdirectories for sounds.
	 * @param directory the directory to get the sounds from
	 */
	protected void loadSounds(final File directory) {
		for (final String s : directory.list()) {
			System.out.println(s);
			final File f = new File(s);
			if (f.isDirectory()) {
				loadSounds(f);
			} else {
				try {
					System.out.println(s);
					final Sound sound = new Sound(s);
					sounds.put(s, sound);
				} catch (UnsupportedAudioFileException | IOException
						| LineUnavailableException e) {
					System.out.println("Could not load sound " + s);
				}
			}
		}
	}

	/**
	 * Recover a sound from it's name
	 * @param soundName the name of the sound
	 * @return the loaded sound, or null if it's not found
	 */
	public Sound getSound(final String soundName) {
		return sounds.get(soundName);
	}
}
