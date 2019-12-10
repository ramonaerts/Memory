package gui;

import controllers.IMemoryController;
import controllers.MemoryController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.eclipse.jetty.util.security.Password;

public class Memory extends Application implements IMemoryGui {

    private final int INPUTWIDTH = 150;
    private final int BORDERSIZE = 10;

    private IMemoryController controller;

    private TextField textFieldPlayerName;
    private PasswordField passwordFieldPlayerPassword;
    private Button loginButton;
    private VBox main;

    @Override
    public void start(Stage primaryStage) {

        Group root = new Group();
        main = new VBox();
        main.setPrefHeight(500);
        main.setPrefWidth(300);
        main.setAlignment(Pos.CENTER);

        textFieldPlayerName = new TextField();
        textFieldPlayerName.setMinWidth(INPUTWIDTH);
        main.getChildren().add(textFieldPlayerName);

        passwordFieldPlayerPassword = new PasswordField();
        passwordFieldPlayerPassword.setMinWidth(INPUTWIDTH);
        main.getChildren().add(passwordFieldPlayerPassword);

        loginButton = new Button();
        loginButton.setMinWidth(INPUTWIDTH);
        loginButton.setText("Login");
        loginButton.setOnAction(
            (EventHandler) event -> {
                try {
                    registerPlayer();
                } catch (Exception e) {
                    System.out.println("Register Player error: {}" + e.getMessage());
                }
            });
        main.getChildren().add(loginButton);

        root.getChildren().add(main);

        Scene scene1 = new Scene(root);

        primaryStage.setTitle("Login memory");
        primaryStage.setScene(scene1);
        primaryStage.show();

        controller = new MemoryController(this);
    }

    private void registerPlayer(){
        controller.RegisterPlayer(textFieldPlayerName.getText(), passwordFieldPlayerPassword.getText());
    }


    public static void main(String[] args) {
        launch(args);
    }
}
