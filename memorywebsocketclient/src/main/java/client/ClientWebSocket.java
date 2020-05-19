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
    private static Logger log = Logger.getLogger(ClientWebSocket.class.getName());

    private Session session;

    private static ClientWebSocket instance = null;

    private boolean isRunning = false;

    public static ClientWebSocket getInstance() {
        if (instance == null) {
            instance = new ClientWebSocket();
        }
        return instance;
    }

    @Override
    public void start() {
        log.info("[WebSocket Client start connection]");
        if (!isRunning) {
            startClient();
            isRunning = true;
        }
    }

    @Override
    public void stop() {
        log.info("[WebSocket Client stop]");
        if (isRunning) {
            stopClient();
            isRunning = false;
        }
    }

    private void startClient() {
        log.info("[WebSocket Client start]");
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
        log.info("[WebSocket Client stop]");
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
        log.info("[WebSocket Client connection error] " + cause.toString());
    }

    @OnClose
    public void onWebSocketClose(CloseReason reason){
        log.info("[WebSocket Client close session] " + session.getRequestURI());
        log.info(" for reason " + reason);
        session = null;
    }

    private void sendMessageToServer(String message)
    {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException ex) {
            log.info("[WebSocket Client couldn't send to server] " + session.getRequestURI());
        }
    }

    public void send(Object object, Object operation)
    {
        SocketMessageGenerator generator = new SocketMessageGenerator();
        String msg = generator.generateMessageString(object, operation);
        sendMessageToServer(msg);
    }
}
