package com.jadifans.opert;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AlertPlayer {

    Media media;
    MediaPlayer mediaPlayer;
    String mediaPath;
    public AlertPlayer(String path)   {
        this.mediaPath = path;

    }

    public void playAlert()   {
        //bad audio file addressing was a big trouble for me > I could run the application on my device but on other pcs  i couldn't run it.
        //
        media = new Media(getClass().getResource(mediaPath).toExternalForm());
        //Instantiating MediaPlayer class
        mediaPlayer = new MediaPlayer(media);
        //by setting this property to true, the audio will be played
       // mediaPlayer.setAutoPlay(true);

        mediaPlayer.stop();
        mediaPlayer.play();
    }
}
