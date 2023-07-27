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
        textArea.setFont(Font.font("Arial", FontWeight.NORMAL, 21));

        textArea.setWrapText(true);
        textArea.setText("hello sdlsd  dsld fsd  slf sfl sfsdl kfsdlf sdflksd flsd fsldf sdlf slfj sdlfs fls fslf slf sdlf ,mxcn sl ljrweljf lsdflfsd sfdl sdflsd fs \n " +
                "le d sl a\n" +
                "vied ds d fd sdl f sfs fdsl fs  \n" +
                "sddl fs  \n" +
                "sdf sdfs lfsdlfk sd fslf s\n " +
                "slfjs flsd fslk \n fd sfsdf lsdfj sd;lfs f s;l" +
                "fsf sdljsd fljf\n" +
                "le d sl a\n" +
                "vied ds d fd sdl f sfs fdsl fs  \n" +
                "sddl fs  \n" +
                "sdf sdfs lfsdlfk sd fslf s\n " +
                "slfjs flsd fslk \n fd sfsdf lsdfj sd;lfs f s;l" +
                "fsf sdljsd fljf\n" +                "le d sl a\n" +
                "vied ds d fd sdl f sfs fdsl fs  \n" +
                "sddl fs  \n" +
                "sdf sdfs lfsdlfk sd fslf s\n " +
                "slfjs flsd fslk \n fd sfsdf lsdfj sd;lfs f s;l" +
                "fsf sdljsd fljf\n" +                "le d sl a\n" +
                "vied ds d fd sdl f sfs fdsl fs  \n" +
                "sddl fs  \n" +
                "sdf sdfs lfsdlfk sd fslf s\n " +
                "slfjs flsd fslk \n fd sfsdf lsdfj sd;lfs f s;l" +
                "fsf sdljsd fljf\n" );
     

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        populateScrollPane();
    }
}
