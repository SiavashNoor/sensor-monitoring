package com.jadifans.opert;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.shape.Circle;

public class HelloController {




    @FXML
    public Circle circleshape;
    private double x;
    private double y;



    public void up(ActionEvent e ){
        System.out.println("up");
        circleshape.setCenterY(y-=10);

    }
    public void down (ActionEvent e ){
        System.out.println("down");
        circleshape.setCenterY(y+=10);
    }
    public void left(ActionEvent e ){
        System.out.println("left");
        circleshape.setCenterX(x-=10);
    }
    public void right(ActionEvent e ){
        System.out.println("right"  );
        circleshape.setCenterX(x+=10);
    }

}