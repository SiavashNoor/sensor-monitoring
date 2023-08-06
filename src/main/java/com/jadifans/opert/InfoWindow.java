package com.jadifans.opert;

import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.net.URL;
import java.util.ResourceBundle;

public class InfoWindow implements Initializable {


    public TextArea textArea;

    private void populateScrollPane(){
       
        textArea.setEditable(false);
        textArea.setFont(Font.font("Arial", FontWeight.NORMAL, 16));

        textArea.setWrapText(true);
        textArea.setText("  Opert is a monitoring software designed to assist me and my coworkers in monitoring the temperature and humidity of indoor and outdoor facilities in our office.\n" +

                "  The software  collects its data from a server. If you are interested in successfully running the software, you can visit the README.md file in my GitHub repository and follow the instructions provided there. For testing purposes, you can use my dedicated server with the following configurations:\n" +
                "IP: 49.12.208.81\n" +
                "Port: 1374\n" +

                "  The server-side application is also available on my GitHub. You have the option to either adjust your data provider source to meet Opert's connection protocol requirements or modify the relevant parts of the source code responsible for the connection to suit your needs.\n" +

                "  Please note that the software is free to use for non-commercial purposes. However, if you intend to use it for commercial purposes, you must seek my permission as it is under the MIT License."+"\n" +"               OPERT V1.0 JUN2023" );
     

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        populateScrollPane();
    }
}
