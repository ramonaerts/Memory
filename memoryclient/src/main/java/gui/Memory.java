package gui;

import client.ClientMessageGenerator;
import client.ClientMessageProcessor;
import client.ClientWebSocket;
import interfaces.IController;
import controllers.MemoryController;
import interfaces.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import messaging.ClientHandlerFactory;
import socketcommunication.GameClient;

import java.util.List;

public class Memory extends Application implements IMemoryGui {

    private final int INPUTWIDTH = 150;
    private final int BORDERSIZE = 10;

    private IController controller;

    private String clientPlayer;
    private String clientOpponent;

    private VBox main;
    private Stage loginStage;
    private Stage lobbyStage;
    private Stage gameStage;

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
        loginButton.addEventHandler(ActionEvent.ACTION,actionEvent -> {
            try {
                loginPlayer(textFieldPlayerName.getText(), passwordFieldPlayerPassword.getText());
            }
            catch (Exception e){
                showMessage("No connection to server, try again later");
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
        clientPlayer = username;
        controller.loginPlayer(username, password);
    }

    public void loginResult(boolean loginresult)
    {
        Platform.runLater(() ->
        {
            if(loginresult) lobby();
            else
            {
                showMessage("Wrong login credentials");
                clientPlayer = "";
            }
        });
    }

    private ListView<String> onlinePlayers;

    private void lobby()
    {
        GridPane grid;
        grid = new GridPane();
        grid.setHgap(BORDERSIZE);
        grid.setVgap(BORDERSIZE);
        grid.setPadding(new Insets(BORDERSIZE,BORDERSIZE,BORDERSIZE,BORDERSIZE));

        Group root = new Group();
        Scene scene1 = new Scene(root, 500, 500);
        root.getChildren().add(grid);
        Font lobbyFont = new Font(15);

        Label onlinePlayersLabel = new Label("Players in lobby:");
        onlinePlayersLabel.setFont(new Font(12));
        onlinePlayersLabel.setLayoutX(300);
        onlinePlayersLabel.setLayoutY(0);
        root.getChildren().add(onlinePlayersLabel);

        onlinePlayers = new ListView<>();
        onlinePlayers.setPrefWidth(200);
        onlinePlayers.setPrefHeight(475);
        onlinePlayers.setLayoutX(300);
        onlinePlayers.setLayoutY(25);
        root.getChildren().add(onlinePlayers);

        Button startGameButton = new Button();
        startGameButton.setText("Start new game");
        startGameButton.setFont(lobbyFont);
        startGameButton.setPrefWidth(INPUTWIDTH);
        startGameButton.setPrefHeight(50);
        startGameButton.addEventHandler(ActionEvent.ACTION,actionEvent -> {
            try {
                controller.startGame();
            }
            catch (Exception e){
                showMessage("No connection to server, try again later");
            }
        });
        grid.add(startGameButton, 6, 2, 1, 1);

        Button searchGameButton = new Button();
        searchGameButton.setText("Search for game");
        searchGameButton.setFont(lobbyFont);
        searchGameButton.setPrefWidth(INPUTWIDTH);
        searchGameButton.setPrefHeight(50);
        searchGameButton.addEventHandler(ActionEvent.ACTION,actionEvent -> {
            try {
                controller.joinGame();
            }
            catch (Exception e){
                showMessage("No connection to server, try again later");
            }
        });
        grid.add(searchGameButton, 6, 7, 1, 1);

        loginStage.close();
        this.lobbyStage = new Stage();
        lobbyStage.setTitle("Lobby");
        lobbyStage.setScene(scene1);
        lobbyStage.show();
    }

    public void updateLobby(List<String> players)
    {
        Platform.runLater(() -> {
            onlinePlayers.getItems().clear();
            for (String player : players)
            {
                onlinePlayers.getItems().add(player);
            }
        });
    }

    public void startGameResult(boolean startResult){
        Platform.runLater(() ->
        {
            if(startResult)gameScreen();
            else showMessage("Game cant be started, try again later.");
        });
    }

    public void joinGameResult(boolean joinResult, String opponentName){
        Platform.runLater(() ->
        {
            if(joinResult)
            {
                clientOpponent = opponentName;
                gameScreen();
                showMessage("You are playing against: " + opponentName + ", you can start playing now.");
            }
            else showMessage("Could not find an available game to join, try again later, or start a game yourself.");
        });
    }

    public void playerJoinsGame(String opponentName)
    {
        Platform.runLater(() ->
        {
            clientOpponent = opponentName;
            showMessage(opponentName + " has joined your game, you can start playing now.");
        });
    }

    private void gameScreen()
    {
        GridPane grid = new GridPane();
        grid.setHgap(BORDERSIZE);
        grid.setVgap(BORDERSIZE);
        grid.setPadding(new Insets(BORDERSIZE,BORDERSIZE,BORDERSIZE,BORDERSIZE));

        Group root = new Group();
        Scene scene1 = new Scene(root, 900, 750);
        root.getChildren().add(grid);

        Rectangle memoryField = new Rectangle(10, 75, 700,500);
        memoryField.setFill(Color.SEAGREEN);
        root.getChildren().add(memoryField);
        Font cardFont = new Font(17);

        Button[][] memoryCards = new Button[6][3];
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 3; j++){
                double x = memoryField.getX() + i * (700/6) + 2;
                double y = memoryField.getY() + j * (500/3) + 2;
                Button card = new Button();
                card.setLayoutX(x);
                card.setLayoutY(y);
                card.setPrefHeight(150);
                card.setPrefWidth(100);
                card.setStyle("-fx-background-color: #ffffff; ");
                card.setText("?");
                card.setFont(cardFont);
                card.setVisible(true);
                int xpos = i;
                int ypos = j;
                card.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        //TODO: send to server with xpos and ypos.
                    }
                });
                memoryCards[i][j] = card;
                root.getChildren().add(card);
            }
        }

        this.gameStage = new Stage();
        gameStage.setTitle("Memory game");
        gameStage.setScene(scene1);
        gameStage.show();
        lobbyStage.close();
    }

    private void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Memory");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
