package client;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import interfaces.IClientMessageProcessor;
import interfaces.IClientWebSocket;
import messages.*;
import org.apache.log4j.Logger;
import serialization.*;

@ClientEndpoint
public class ClientWebSocket implements IClientWebSocket
{
    static Logger log = Logger.getLogger(ClientWebSocket.class.getName());

    private Session session;

    private static ClientWebSocket instance = null;

    boolean isRunning = false;

    public static ClientWebSocket getInstance() {
        if (instance == null) {
            instance = new ClientWebSocket();
        }
        return instance;
    }

    @Override
    public void start() {
        System.out.println("[WebSocket Client start connection]");
        if (!isRunning) {
            startClient();
            isRunning = true;
        }
    }

    @Override
    public void stop() {
        System.out.println("[WebSocket Client stop]");
        if (isRunning) {
            stopClient();
            isRunning = false;
        }
    }

    private void startClient() {
        System.out.println("[WebSocket Client start]");
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            String uri = "ws://localhost:8095/memory";
            container.connectToServer(this, new URI(uri));

        } catch (IOException | URISyntaxException | DeploymentException ex) {
            // do something useful eventually
            log.info(ex);
        }
    }

    /**
     * Stop the client when it is running.
     */
    private void stopClient(){
        System.out.println("[WebSocket Client stop]");
        try {
            if(session != null) session.close();

        } catch (IOException ex){
            // do something useful eventually
            log.info(ex);
        }
    }

    @OnOpen
    public void onWebSocketConnect(Session session){
        this.session = session;
    }

    @OnMessage
    public void onWebSocketText(String message, Session session){
        onWebSocketMessageReceived(message, session.getId());
    }

    private IClientMessageProcessor messageProcessor;

    @Override
    public void setMessageProcessor(IClientMessageProcessor handler) {
        this.messageProcessor = handler;
    }

    public void onWebSocketMessageReceived(String message, String sessionId)
    {
        Serializer ser = Serializer.getSerializer();
        SocketMessage msg = ser.deserialize(message, SocketMessage.class);
        messageProcessor.processMessage(sessionId, msg.getMessageOperation(), msg.getMessageData());
    }

    @OnError
    public void onWebSocketError(Session session, Throwable cause) {
        System.out.println("[WebSocket Client connection error] " + cause.toString());
    }

    @OnClose
    public void onWebSocketClose(CloseReason reason){
        System.out.print("[WebSocket Client close session] " + session.getRequestURI());
        System.out.println(" for reason " + reason);
        session = null;
    }

    private void sendMessageToServer(String message)
    {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException ex) {
            System.out.print("[WebSocket Client couldn't send to server] " + session.getRequestURI());
        }
    }

    public void send(Object object, Object operation)
    {
        SocketMessageGenerator generator = new SocketMessageGenerator();
        String msg = generator.generateMessageString(object, operation);
        sendMessageToServer(msg);
    }
}
