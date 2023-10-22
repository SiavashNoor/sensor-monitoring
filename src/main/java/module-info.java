module com.jadifans.opert {

    requires java.base;
    requires java.desktop;
    requires javafx.controls;
    requires javafx.fxml;
    //newly added :

     requires javafx.base;
   requires javafx.graphics;
    requires javafx.media;





    // add icon pack modules
    requires org.kordamp.ikonli.fontawesome5;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires  org.jsoup;
    requires com.google.gson;

    requires java.xml;
    requires java.xml.bind;
    requires com.sun.xml.bind;
    ////was here:
    opens com.jadifans.opert to javafx.fxml,java.xml.bind;
    exports com.jadifans.opert;


    ////Important Note :
    ////
    ////
    //// you have to add modules here . otherwise you will get errors .:I was getting 'could not find the class exception'
    //// because I hadn't added the modules here .wow take a long time to solve the error.

}