package gui;

import client.ClientMessageGenerator;
import client.ClientMessageProcessor;
import client.ClientWebSocket;
import interfaces.IController;
import controllers.MemoryController;
import interfaces.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import messaging.ClientHandlerFactory;
import socketcommunication.GameClient;

public class Memory extends Application implements IMemoryGui {

    private final int INPUTWIDTH = 150;
    private final int BORDERSIZE = 10;

    private IController controller;

    private TextField textFieldPlayerName;
    private PasswordField passwordFieldPlayerPassword;
    private Button loginButton;
    private VBox main;

    @Override
    public void start(Stage primaryStage) {

        IClientWebSocket socket = new ClientWebSocket();
        IClientMessageGenerator generator = new ClientMessageGenerator(socket);

        IGameClient gameClient = new GameClient(generator);
        IClientHandlerFactory factory = new ClientHandlerFactory();

        IClientMessageProcessor processor = new ClientMessageProcessor(factory);
        socket.setMessageProcessor(processor);

        socket.start();
        processor.registerGameClient(gameClient);

        controller = new MemoryController(this, gameClient);

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
    }

    private void registerPlayer(){
        controller.RegisterPlayer(textFieldPlayerName.getText(), passwordFieldPlayerPassword.getText());
    }

    public void showPlayer(String username){
        String test = "ajksdfljasd";
    }

    public static void main(String[] args) {
        launch(args);
    }
}
