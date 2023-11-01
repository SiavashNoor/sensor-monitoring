package com.jadifans.opert;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;



public class OpertApplication extends Application  {
    Parameters params;
    MainScene mainScene;
    @Override
    public void start(Stage stage) throws IOException {

        showUI(stage);

    }
    @Override
    public void stop(){
        System.exit(0);
    }


    public void showUI(Stage stage) throws IOException {
        params = getParameters();
        //this is for main application : do not delete this ;
        FXMLLoader loader = new FXMLLoader(OpertApplication.class.getResource("mainScene.fxml"));
        Parent root = loader.load();
         mainScene = loader.getController();
        Scene scene = new Scene(root);
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


        if(ApplicationIsOpenedThroughConfigFile(params)){
            startThroughThisConfig();
        }
    }

     void startThroughThisConfig() {
        mainScene.startUpApplicationWithThisConfig(params.getRaw().get(0));
    }

    private boolean ApplicationIsOpenedThroughConfigFile(Parameters params) {
        boolean isOpenedFromConfigFile =false;
        if (params.getRaw().toArray().length != 0) {
            if(!params.getRaw().toArray()[0].toString().isBlank()){
                isOpenedFromConfigFile =true;
            }
        }
        return isOpenedFromConfigFile;
    }

    public static void main(String[] args) {
        launch(args);
    }
}