package smartchart;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.collections.ObservableList;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import server.Loggable;


import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

public class Controller implements Initializable, Loggable {
    @FXML
    public PasswordField passwordField;

    @FXML
    public TextField loginTextField;

    @FXML
    public Button loginButton;

    @FXML
    public Label logintxt;

    @FXML
    public Label passwordtxt;

    @FXML
    public MenuItem menuRegistration;

    @FXML
    public MenuItem connect;

    @FXML
    public ComboBox usersList;

    @FXML
    private Button button;

    @FXML
    private TextField textField;

    @FXML
    private TextArea textArea;
    private ClientIOHandler handler;
    private RegistrationController registrationController;
    private Stage regStage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setAuthenticated(false);
        regStage = createRegWindow();
        setTitle("Not connected");
    }

    public void setAuthenticated(boolean status) {
        textField.setVisible(status);
        textField.setManaged(status);
        button.setVisible(status);
        button.setManaged(status);
        usersList.setManaged(status);
        usersList.setVisible(status);

        loginTextField.setVisible(!status);
        passwordField.setVisible(!status);
        loginTextField.setManaged(!status);
        passwordField.setManaged(!status);
        logintxt.setVisible(!status);
        logintxt.setManaged(!status);
        passwordtxt.setVisible(!status);
        passwordtxt.setManaged(!status);
        loginButton.setVisible(!status);
        loginButton.setManaged(!status);


        if (status) {
            setTitle(handler.getNick());
        }

        menuRegistration.setDisable(status);
    }


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

    public void printMessage(String name, String msg) {
        synchronized (textArea) {
            textArea.appendText("[" + name + "]:" + msg + "\n");
        }
    }

    private void sendMessage() {
        if ( inValidate() ) {
            synchronized (textArea) {
                handler.sendMessageToUsers(textField.getText());
                //textArea.appendText(textField.getText()+"\n");
            }
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

    public void btnAuth(MouseEvent mouseEvent) {
        if ( handler == null || !handler.isConnected()) {
            handler = new ClientIOHandler(this, this);
        }
        handler.tryAuthenticate(loginTextField.getText(),passwordField.getText());
    }

    private void setTitle(String nick) {
        Platform.runLater(() -> {
            ((Stage) textField.getScene().getWindow()).setTitle("SmartChat " + nick);
        });
    }


    private Stage createRegWindow() {
        Stage stage = null;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("registration.fxml"));
            Parent root = fxmlLoader.load();

            stage = new Stage();
            stage.setTitle("Registration");
            stage.setScene(new Scene(root, 300,200));
            stage.initStyle(StageStyle.UTILITY);
            stage.initModality(Modality.APPLICATION_MODAL);

            registrationController = fxmlLoader.getController();
            registrationController.controller = this;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return stage;
    }


    public void onRegClicked(ActionEvent actionEvent) {
        regStage.show();
    }

    public void tryRegistration(String login, String password, String nickname) {
        if ( handler == null || !handler.isConnected()) {
            handler = new ClientIOHandler(this, this);
        }
        handler.tryRegistration(login, password, nickname);
    }

    public void onConnectClicked(ActionEvent actionEvent) {
        if ( handler == null || !handler.isConnected()) {
            handler = new ClientIOHandler(this, this);
            connect.setText("Disconnect");
            setTitle("");
        } else {
            handler.disconnect();
            connect.setText("Disconnect");
            setTitle("[Not connected]");
        }
    }
}
