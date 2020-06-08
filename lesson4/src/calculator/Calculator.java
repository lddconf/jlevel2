package calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Calculator extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ui.fxml"));
        primaryStage.setTitle("Calculator");

        Scene scene = new Scene(root, 320, 200);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();


    }
}
