package com.mycompany.mavenproject1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;

import java.io.IOException;
import java.sql.SQLException;

public class App extends Application {

    private static Scene scene;  
    private static Object data;
    

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("researcher"), 1910, 1020);
        stage.setScene(scene);
        stage.show();

    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
  
  
    public static void setRoot(String fxml, Object data) throws IOException {
        App.data = data;
        setRoot(fxml);
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection("jdbc:mysql://localhost/session_nov", "root", "3009");
    }

    public static void main(String[] args) {
        launch();
    }
    
    public static Object getData() {
        return data;
    }
    }