package task1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.collections.ObservableList;

import java.util.Collections;

public class Controller {
    @FXML
    private Button button;
    @FXML
    private TextField textField;
    @FXML
    private TextArea textArea;

    private void setupErrorView(boolean disable) {
        ObservableList<String> styleClass = textField.getStyleClass();
        if ( !disable ) {
            if (!styleClass.contains("error")) {
                styleClass.add("error");
            }
        } else {
            if (styleClass.contains("error")) {
                styleClass.removeAll(Collections.singleton("error"));
            }
        }
    }

    private boolean inValidate() {
        boolean status = true;
        if ( textField.getText().length() == 0 ) {
            status = false;
        }
        setupErrorView(status);
        return status;
    }

    private void sendMessage() {

        if ( inValidate() ) {
            textArea.appendText(textField.getText()+"\n");
            textField.clear();
        }
    }

    public void btnClicked(MouseEvent mouseEvent) {
        sendMessage();
    }

    public void onExitClicked(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void onKeyPressed(KeyEvent keyEvent) {
        if ( keyEvent.getCode().equals(KeyCode.ENTER) ) {
            sendMessage();
        } else {
            setupErrorView(true);
        }
    }
}
