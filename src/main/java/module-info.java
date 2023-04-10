module at.ac.fhcampuswien.fhmdb {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.jfoenix;

    //Added these to make them work:
    requires okhttp3;
    requires com.google.gson;
    opens at.ac.fhcampuswien.fhmdb.models to com.google.gson, javafx.fxml;


    opens at.ac.fhcampuswien.fhmdb to javafx.fxml;
    exports at.ac.fhcampuswien.fhmdb.models;
    exports at.ac.fhcampuswien.fhmdb;
}