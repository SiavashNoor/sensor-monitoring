package com.jadifans.opert;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;


public class HelloApplication extends Application  {

    @Override
    public void start(Stage stage) throws IOException {
        showUI(stage);

    }
   /* @Override
    public void stop(){

    }*/


    public void showUI(Stage stage) throws IOException {

        //this is for main application : do not delete this ;
        FXMLLoader fxmlLoader1 = new FXMLLoader(HelloApplication.class.getResource("mainScene.fxml"));
        Scene scene = new Scene(fxmlLoader1.load());
        //for main program change this to UNDECORATED.
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("monitoring");
        stage.setScene(scene);
        stage.setFullScreen(false);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}