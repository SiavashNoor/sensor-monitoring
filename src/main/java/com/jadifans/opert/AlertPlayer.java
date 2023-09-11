package com.jadifans.opert;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AlertPlayer {

    Media media;
    MediaPlayer mediaPlayer;
    public AlertPlayer()   {
        //bad audio file addressing was a big trouble for me > I could run the application on my device but on other pcs  i couldn't run it.
        //
         media = new Media(getClass().getResource("/com/jadifans/opert/soundEffects/alarm.mp3").toExternalForm());
        //Instantiating MediaPlayer class
         mediaPlayer = new MediaPlayer(media);
        //by setting this property to true, the audio will be played
    }

    public void playAlert()   {
       // mediaPlayer.setAutoPlay(true);

        mediaPlayer.stop();
        mediaPlayer.play();
    }
}
