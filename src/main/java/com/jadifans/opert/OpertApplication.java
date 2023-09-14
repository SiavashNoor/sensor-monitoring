package com.jadifans.opert;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public class OpertApplication extends Application  {

    @Override
    public void start(Stage stage) throws IOException {
        showUI(stage);
    }
    @Override
    public void stop(){
        System.exit(0);
    }


    public void showUI(Stage stage) throws IOException {

        //this is for main application : do not delete this ;
        FXMLLoader loader = new FXMLLoader(OpertApplication.class.getResource("mainScene.fxml"));
        Scene scene = new Scene(loader.load());
        scene.setFill(Color.TRANSPARENT);
        //for main program change this to UNDECORATED.


        stage.initStyle(StageStyle.UNDECORATED);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("monitoring");
        stage.setScene(scene);
        stage.setFullScreen(false);
        stage.setMinWidth(400);
        stage.setMinHeight(340);
        ResizeHelper.addResizeListener(stage);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}