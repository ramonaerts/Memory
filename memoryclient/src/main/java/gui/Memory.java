package gui;

import client.ClientMessageGenerator;
import client.ClientMessageProcessor;
import client.ClientWebSocket;
import interfaces.IController;
import controllers.MemoryController;
import interfaces.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import messaging.ClientHandlerFactory;
import socketcommunication.GameClient;

import java.util.List;

public class Memory extends Application implements IMemoryGui {

    private final int INPUTWIDTH = 150;
    private final int BORDERSIZE = 10;

    private IController controller;

    private VBox main;
    private Stage loginStage;
    private Stage lobbyStage;

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
        //main.setAlignment(Pos.CENTER);

        TextField textFieldPlayerName = new TextField();
        textFieldPlayerName.setMinWidth(INPUTWIDTH);
        textFieldPlayerName.setMinWidth(20.20);
        main.getChildren().add(textFieldPlayerName);

        PasswordField passwordFieldPlayerPassword = new PasswordField();
        passwordFieldPlayerPassword.setMinWidth(INPUTWIDTH);
        main.getChildren().add(passwordFieldPlayerPassword);

        Button loginButton = new Button();
        loginButton.setMinWidth(INPUTWIDTH);
        loginButton.setText("Login");
        loginButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                loginPlayer(textFieldPlayerName.getText(), passwordFieldPlayerPassword.getText());
            }
        });
        main.getChildren().add(loginButton);

        root.getChildren().add(main);

        Scene scene1 = new Scene(root);

        this.loginStage = primaryStage;
        loginStage.setTitle("Login memory");
        loginStage.setScene(scene1);
        loginStage.show();
    }

    private void loginPlayer(String username, String password){
        controller.loginPlayer(username, password);
    }

    public void loginResult(boolean loginresult){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(loginresult) Lobby();
                else showMessage("Wrong user credentials");
            }
        });

    }

    private void showMessage(final String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Memory");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private ListView<String> onlinePlayers;

    private void Lobby()
    {
        GridPane grid;
        grid = new GridPane();
        grid.setHgap(BORDERSIZE);
        grid.setVgap(BORDERSIZE);
        grid.setPadding(new Insets(BORDERSIZE,BORDERSIZE,BORDERSIZE,BORDERSIZE));

        Group root = new Group();
        Scene scene1 = new Scene(root, 500, 522);
        root.getChildren().add(grid);

        onlinePlayers = new ListView<>();
        onlinePlayers.setPrefWidth(100);
        onlinePlayers.setPrefHeight(500);
        grid.add(onlinePlayers, 27,0,20,1);

        loginStage.close();
        this.lobbyStage = new Stage();
        lobbyStage.setTitle("Lobby");
        lobbyStage.setScene(scene1);
        lobbyStage.show();
    }

    public void updateLobby(String username)
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                onlinePlayers.getItems().add(username);
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
