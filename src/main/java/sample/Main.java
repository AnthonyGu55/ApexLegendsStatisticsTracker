package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        URL url = new File("C:\\Users\\Antoni\\Programming\\IdeaProjects\\ApexStatsJavaFX\\src\\main\\java\\sample\\sample.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("Hello World");

        primaryStage.setScene(new Scene(root, 1920, 1010));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
