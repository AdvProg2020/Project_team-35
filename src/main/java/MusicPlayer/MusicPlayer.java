package MusicPlayer;

import javafx.scene.media.AudioClip;

public class MusicPlayer {
    private static MusicPlayer musicPlayer;
    private MusicPlayer(){

    }
    public static MusicPlayer getInstance() {
        if (MusicPlayer.musicPlayer == null) {
            musicPlayer = new MusicPlayer();
        }
        return musicPlayer;
    }


    private static int musicNumber = 0;
    private static boolean isBGPlaying  = true;

    public boolean isBGMusicPlaying() {
        return isBGPlaying;
    }

    public static void setIsBGPlaying(boolean isBGPlaying) {
        MusicPlayer.isBGPlaying = isBGPlaying;
    }

    private static AudioClip background;


    public void playBGMusic() {
        String path = "../Resources/sounds/b0.m4a";
        AudioClip audioClip = new AudioClip(getClass().getResource(path).toString());
        background = audioClip;
        audioClip.play();
    }

    public void changeBGMusic() {
        musicNumber++;
        background.stop();
        String path = "../Resources/sounds/b" + musicNumber % 5 + ".mp3";
        System.out.println(path);
        AudioClip audioClip = new AudioClip(getClass().getResource(path).toString());
        background = audioClip;
        audioClip.play();
    }



    public void stopBGMusic() {
        background.stop();
    }

    public void playButtonMusic() {
        String path = "../Resources/sounds/button.mp3";
        AudioClip audioClip = new AudioClip(getClass().getResource(path).toString());
        audioClip.play();
    }

    public void playErrorMusic() {
        String path = "../Resources/sounds/error.mp3";
        AudioClip audioClip = new AudioClip(getClass().getResource(path).toString());
        audioClip.play();

    }





}
