package com.jadifans.opert;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.awt.*;
import java.io.IOException;


public class MainScene {


    Stage stage;
    Stage stationStage;
    private  double xOffset = 0;
    private  double yOffset = 0;


    @FXML
    private FontIcon addStation;

    @FXML
    private Hyperlink githubLink;

    @FXML
    private Circle closeButton;

    @FXML
    private Circle minimizeButton;

    @FXML
    private Pane windowBar;


    public void closeApplication(MouseEvent event) {
        stage = (Stage) ((Circle) event.getSource()).getScene().getWindow();
        stage.close();
    }


    public void minimizeApplication(MouseEvent event) {
        stage = (Stage) ((Circle) event.getSource()).getScene().getWindow();
        stage.setIconified(true);

    }

    public void openGithubLink(MouseEvent event) throws IOException {
        //this class give access to desktop utilities
        Desktop desktop = Desktop.getDesktop();
        desktop.browse(java.net.URI.create("https://github.com/SiavashNoor"));
    }


    public MainScene() {

    }

    public void openStationWindow(MouseEvent mouseEvent) throws IOException {

        Parent root =FXMLLoader.load(HelloApplication.class.getResource("stationAdder.fxml"));
        Stage newStage = new Stage();
        newStage.setTitle("Add New Station");
        Scene scene = new Scene(root);
        newStage.setScene(scene);
        newStage.show();

           /* Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("stationAdder.fxml"));
            stationStage= new Stage();
            stationStage.setTitle(" New Station");
            stationStage.setScene(new Scene(root, 450, 450));
            stationStage.initModality(Modality.APPLICATION_MODAL);
            stationStage.show();*/
    }



    public void windowBarPressed(MouseEvent mouseEvent) {
        xOffset = mouseEvent.getX();
        yOffset = mouseEvent.getY();

    }

    public void windowBarDragged(MouseEvent mouseEvent) {
        stage = (Stage) ((Pane) mouseEvent.getSource()).getScene().getWindow();
        stage.setX(mouseEvent.getScreenX()-xOffset);
        stage.setY(mouseEvent.getScreenY()-yOffset);




    }


}

