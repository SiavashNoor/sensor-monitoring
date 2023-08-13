package com.jadifans.opert;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class AlertPlayer {

    Media media;
    MediaPlayer mediaPlayer;
    static String filePath = "E:/javaprg/opert/src/main/resources/com/jadifans/opert/soundEffects/alarm.mp3";

    public AlertPlayer()   {
         media = new Media(new File(filePath).toURI().toString());
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
