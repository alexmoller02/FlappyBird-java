package Assets;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;

public class SoundPlayer {
    private static final HashMap<String, Clip> soundClips = new HashMap<>();
    private static final Random random = new Random();

    /**
     * Preload all sounds into memory to avoid lag during gameplay.
     * Call this once at game startup.
     */
    public static void preload() {
        load("Death", "../Resources/Sounds/Death.wav");
        load("Fall", "../Resources/Sounds/Fall.wav");
        load("Point", "../Resources/Sounds/Point.wav");

        // Load all whoosh variations
        for (int i = 1; i <= 9; i++) {
            load("Whoosh" + i, "../Resources/Sounds/Whoosh" + i + ".wav");
        }
    }

    /**
     * Load a sound file and store it in memory.
     */
    private static void load(String name, String resourcePath) {
        try {
            URL url = SoundPlayer.class.getResource(resourcePath);
            if (url == null) {
                System.err.println("Could not find sound file: " + resourcePath);
                return;
            }

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            soundClips.put(name, clip);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Failed to load sound: " + name);
            e.printStackTrace();
        }
    }

    /**
     * Play a preloaded sound by name.
     */
    public static void play(String name) {
        Clip clip = soundClips.get(name);
        if (clip != null) {
            if (clip.isRunning()) {
                clip.stop(); // stop and rewind if still playing
            }
            clip.setFramePosition(0);
            clip.start();
        } else {
            System.err.println("Sound not found: " + name);
        }
    }

    /**
     * Stop a preloaded sound by name.
     */
    public static void stop(String name) {
        Clip clip = soundClips.get(name);
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    /**
     * Stop all currently playing clips.
     */
    public static void stopAll() {
        for (Clip clip : soundClips.values()) {
            if (clip.isRunning()) {
                clip.stop();
            }
        }
    }

    /**
     * Play a random "Whoosh" sound (from Whoosh1 to Whoosh9).
     */
    public static void playRandomWhoosh() {
        int index = 1 + random.nextInt(9); // 1 to 9
        play("Whoosh" + index);
    }
}
