package world.ucode.utils;

import javax.sound.sampled.*;
import javax.swing.*;
import java.net.URL;

public class Utils {

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static synchronized void playSound(String name) {

        new Thread(new Runnable() {
            public void run() {
                try {
                    URL url = this.getClass().getClassLoader().getResource(name);
                    AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioIn);
                    clip.start();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }).start();
    }
}
