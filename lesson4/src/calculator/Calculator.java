package calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

        Scene scene = new Scene(root, 640, 480);
        primaryStage.setScene(scene);


       // initGUI(root);

        primaryStage.show();


    }

    private static void addButton(HBox layout, String name) {
        Button button1 = new Button(name);
        button1.setOnMouseClicked(e->{

        });

    }

    public static void initGUI(Parent root) {
        for (Node child : root.getChildrenUnmodifiable()) {
            if (( child.getId().equals("verticalLayout")) && ( child instanceof VBox)) {
/*
                HBox hbox1 = new HBox();

                Button button2 = new Button("Button 2");
                Button button3 = new Button("Button 3");

                HBox hbox21 = new HBox();
                Button button4 = new Button("Button 4");
                Button button5 = new Button("Button 5");
                Button button6 = new Button("Button 6");

                GridPane gridPane = (GridPane)(child);
                //gridPane.getColumnConstraints().
                gridPane.add(button1, 0, 0 );
                gridPane.add(button2, 0, 1);
                gridPane.add(button3, 0, 2);
                gridPane.add(button4, 0, 3);
                gridPane.add(button5, 0, 4);
                */

            }
        }
    }
}
