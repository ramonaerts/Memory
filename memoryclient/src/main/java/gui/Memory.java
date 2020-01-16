package gui;

import client.ClientMessageGenerator;
import client.ClientMessageProcessor;
import client.ClientWebSocket;
import enums.GameResult;
import interfaces.IController;
import controllers.MemoryController;
import interfaces.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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
import models.Coordinate;
import models.Player;
import socketcommunication.GameClient;

import java.util.ArrayList;
import java.util.List;

public class Memory extends Application implements IMemoryGui {

    private final int INPUTWIDTH = 150;
    private final int BORDERSIZE = 10;

    private IClientWebSocket socket;
    private IController controller;

    private Player currentPlayer;

    private Stage loginStage;
    private Stage lobbyStage;
    private Stage gameStage;

    @Override
    public void start(Stage primaryStage) {

        socket = new ClientWebSocket();
        IClientMessageGenerator generator = new ClientMessageGenerator(socket);

        IGameClient gameClient = new GameClient(generator);
        IClientHandlerFactory factory = new ClientHandlerFactory();

        IClientMessageProcessor processor = new ClientMessageProcessor(factory);
        socket.setMessageProcessor(processor);

        socket.start();
        processor.registerGameClient(gameClient);

        controller = new MemoryController(this, gameClient);

        GridPane grid = new GridPane();
        grid.setHgap(BORDERSIZE);
        grid.setVgap(BORDERSIZE);
        grid.setPadding(new Insets(BORDERSIZE,BORDERSIZE,BORDERSIZE,BORDERSIZE));

        Group root = new Group();
        root.getChildren().add(grid);
        Scene scene = new Scene(root, 300, 300);

        TextField textFieldPlayerName = new TextField();
        textFieldPlayerName.setMinWidth(200);
        textFieldPlayerName.setLayoutX(50);
        textFieldPlayerName.setLayoutY(50);
        root.getChildren().add(textFieldPlayerName);

        PasswordField passwordFieldPlayerPassword = new PasswordField();
        passwordFieldPlayerPassword.setMinWidth(200);
        passwordFieldPlayerPassword.setLayoutX(50);
        passwordFieldPlayerPassword.setLayoutY(100);
        root.getChildren().add(passwordFieldPlayerPassword);

        Button loginButton = new Button();
        loginButton.setMinWidth(200);
        loginButton.setText("Login");
        loginButton.setLayoutX(50);
        loginButton.setLayoutY(150);
        loginButton.addEventHandler(ActionEvent.ACTION,actionEvent -> {
            try {
                controller.loginPlayer(textFieldPlayerName.getText(), passwordFieldPlayerPassword.getText());
            }
            catch (Exception e){
                showMessage("No connection to server, try again later");
            }
        });
        root.getChildren().add(loginButton);

        Button registerButton = new Button();
        registerButton.setMinWidth(200);
        registerButton.setText("Register");
        registerButton.setLayoutX(50);
        registerButton.setLayoutY(200);
        registerButton.addEventHandler(ActionEvent.ACTION,actionEvent -> {
            try {
                controller.registerPlayer(textFieldPlayerName.getText(), passwordFieldPlayerPassword.getText());
            }
            catch (Exception e){
                showMessage("No connection to server, try again later");
            }
        });
        root.getChildren().add(registerButton);

        this.loginStage = new Stage();
        loginStage.setTitle("Login memory");
        loginStage.setScene(scene);
        loginStage.show();
    }

    public void loginResult(boolean loginresult, Object player)
    {
        Platform.runLater(() ->
        {
            if(loginresult)
            {
                currentPlayer = (Player) player;
                lobby();
            }
            else
            {
                showMessage("Wrong login credentials");
                currentPlayer = null;
            }
        });
    }

    public void registerResult(boolean registerResult, Object player){
        Platform.runLater(() ->
        {
            if(registerResult)
            {
                currentPlayer = (Player) player;
                lobby();
            }
            else
            {
                showMessage("Username already exists, choose another one");
                currentPlayer = null;
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

    private List<Player> inGamePlayers = new ArrayList<>();
    private Button[][] memoryCards;
    private int gameId;
    private int firstPlayerScore = 0;
    private int secondPlayerScore = 0;

    private Label playerName = new Label();
    private Label playerScore = new Label();
    private Label opponentName = new Label();
    private Label opponentScore = new Label();

    private ListView<String> gameChat = new ListView<>();

    private void gameScreen()
    {
        GridPane grid = new GridPane();
        grid.setHgap(BORDERSIZE);
        grid.setVgap(BORDERSIZE);
        grid.setPadding(new Insets(BORDERSIZE,BORDERSIZE,BORDERSIZE,BORDERSIZE));

        Group root = new Group();
        Scene scene1 = new Scene(root, 750, 750);
        root.getChildren().add(grid);

        Font statsFont = new Font(15);

        Rectangle playerRectangle = new Rectangle(35, 10, 325, 70);
        playerRectangle.setStrokeWidth(5);
        playerRectangle.setStroke(Color.LIGHTBLUE);

        Rectangle opponentRectangle = new Rectangle(385, 10, 325, 70);
        opponentRectangle.setStrokeWidth(5);
        opponentRectangle.setStroke(Color.SALMON);

        setStandardLabels(root, statsFont, playerRectangle, 50, 20);
        setStandardLabels(root, statsFont, opponentRectangle, 400, 20);

        setVariableLabels(root, statsFont, playerName, "null", 100, 20);
        setVariableLabels(root, statsFont, playerScore, "0", 100, 45);
        setVariableLabels(root, statsFont, opponentName, "Waiting for player", 450, 20);
        setVariableLabels(root, statsFont, opponentScore, "0", 450, 45);
        
        Rectangle memoryField = new Rectangle(25, 90, 685,487);
        memoryField.setFill(Color.SEAGREEN);
        root.getChildren().add(memoryField);
        Font cardFont = new Font(20);

        memoryCards = new Button[6][3];
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 3; j++){
                double x = memoryField.getX() + i * (700/(double)6) + 2;
                double y = memoryField.getY() + j * (500/(double)3) + 2;
                Button card = new Button();
                card.setLayoutX(x);
                card.setLayoutY(y);
                card.setPrefHeight(150);
                card.setPrefWidth(100);
                card.setStyle("-fx-background-color: #ffffff; ");
                card.setText("?");
                card.setFont(cardFont);
                card.setVisible(true);
                int xpos = j;
                int ypos = i;
                card.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> controller.turnCard(xpos, ypos, gameId));
                memoryCards[i][j] = card;
                root.getChildren().add(card);
            }
        }

        gameChat.setPrefWidth(700);
        gameChat.setPrefHeight(125);
        gameChat.setLayoutX(25);
        gameChat.setLayoutY(600);
        root.getChildren().add(gameChat);

        this.gameStage = new Stage();
        gameStage.setTitle("Memory game");
        gameStage.setScene(scene1);
        gameStage.show();
        lobbyStage.close();
    }

    private void setVariableLabels(Group root, Font statsFont, Label label, String text, int x, int y) {
        label.setText(text);
        label.setLayoutX(x);
        label.setLayoutY(y);
        label.setFont(statsFont);
        root.getChildren().add(label);
    }

    private void setStandardLabels(Group root, Font statsFont, Rectangle opponentRectangle, int x, int y) {
        opponentRectangle.setFill(Color.WHITE);
        root.getChildren().add(opponentRectangle);

        Label opponentLabel = new Label("Player: ");
        opponentLabel.setLayoutX(x);
        opponentLabel.setLayoutY(y);
        opponentLabel.setFont(statsFont);
        root.getChildren().add(opponentLabel);

        Label opponentScoreLabel = new Label("Points: ");
        opponentScoreLabel.setLayoutX(x);
        opponentScoreLabel.setLayoutY(y + (double)25);
        opponentScoreLabel.setFont(statsFont);
        root.getChildren().add(opponentScoreLabel);
    }

    public void showCardInfo(int cardValue, Object coordinate, int playerNr){
        Platform.runLater(() ->
        {
            Coordinate coor = (Coordinate) coordinate;
            Button currentButton = memoryCards[coor.getX()][coor.getY()];
            currentButton.setText(Integer.toString(cardValue));
            setColorBasedOnPlayerNumber(currentButton, playerNr);
        });
    }

    private void setColorBasedOnPlayerNumber(Button cardButton, int playerNr){
        if (playerNr == 1) cardButton.setStyle("-fx-border-color: #ADD8E6; -fx-border-width: 3; -fx-background-color: #ffffff;");
        if (playerNr == 2) cardButton.setStyle("-fx-border-color: #FA8072; -fx-border-width: 3; -fx-background-color: #ffffff;");
        //Add multiple if the game will be increased for more players
    }

    public void turnCardBack(Object coordinate) {
        Platform.runLater(() ->
        {
            Coordinate coor = (Coordinate) coordinate;
            Button currentButton = memoryCards[coor.getX()][coor.getY()];
            currentButton.setText("?");
            currentButton.setStyle("-fx-background-color: #ffffff;");
        });
    }

    public void sendPoint(int playerNr){
        Platform.runLater(() ->
        {
            if (playerNr == 1){
                firstPlayerScore++;
                playerScore.setText(Integer.toString(firstPlayerScore));
            }
            if (playerNr == 2){
                secondPlayerScore++;
                opponentScore.setText(Integer.toString(secondPlayerScore));
            }
        });
    }

    public void gameResult(Object result) {
        Platform.runLater(() ->
        {
            GameResult gameResult = (GameResult) result;
            if (gameResult == GameResult.WIN) messageToGameChat("You have won the game");
            if (gameResult == GameResult.DRAW) messageToGameChat("You have draw the game");
            if (gameResult == GameResult.LOSE) messageToGameChat("You have lost the game");
        });
    }

    public void startGameResult(boolean startResult, int gameId){
        Platform.runLater(() ->
        {
            if(startResult)
            {
                this.gameId = gameId;
                inGamePlayers.add(currentPlayer);
                gameScreen();
                playerName.setText(currentPlayer.getUsername());
            }
            else showMessage("Game cant be started, try again later.");
        });
    }

    public void joinGameResult(boolean joinResult, int gameId, Object opponent){
        Platform.runLater(() ->
        {
            if(joinResult)
            {
                Player opponentPlayer = (Player) opponent;
                inGamePlayers.add(opponentPlayer);
                inGamePlayers.add(currentPlayer);
                this.gameId = gameId;

                gameScreen();
                playerName.setText(opponentPlayer.getUsername());
                opponentName.setText(currentPlayer.getUsername());
                messageToGameChat("You are playing against: " + opponentPlayer.getUsername() + ", you can start playing now.");
            }
            else showMessage("Could not find an available game to join, try again later, or start a game yourself.");
        });
    }

    public void playerJoinsGame(Object opponent)
    {
        Platform.runLater(() ->
        {
            Player opponentPlayer = (Player) opponent;
            inGamePlayers.add(opponentPlayer);
            opponentName.setText(opponentPlayer.getUsername());
            messageToGameChat(opponentPlayer.getUsername() + " has joined your game, you can start playing now.");
        });
    }

    public void messageToGameChat(String message)
    {
        Platform.runLater(() -> gameChat.getItems().add(message));
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
