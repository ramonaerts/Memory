package server;

import interfaces.IServerMessageProcessor;
import interfaces.IServerWebSocket;
import messages.SocketMessage;
import messages.SocketMessageGenerator;
import org.apache.log4j.Logger;
import serialization.Serializer;

import javax.websocket.*;
import javax.inject.Singleton;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Singleton
@ServerEndpoint(value="/memory")
public class ServerWebSocket implements IServerWebSocket {

    private static Logger log = Logger.getLogger(ServerWebSocket.class.getName());

    private IServerMessageProcessor messageProcessor;

    public void setMessageProcessor(IServerMessageProcessor processor)
    {
        this.messageProcessor = processor;
    }

    public IServerMessageProcessor getMessageProcessor() {
        return messageProcessor;
    }

    private static ServerWebSocket instance = null;

    public static ServerWebSocket getInstance(){
        if (instance == null) {
            instance = new ServerWebSocket();
        }
        return instance;
    }

    private static ArrayList<Session> sessions = new ArrayList<>();

    @OnOpen
    public void onConnect(Session session) {
        sessions.add(session);
    }

    private ExecutorService executorService = Executors.newCachedThreadPool();

    @OnMessage
    public void onText(String message, Session session) {
        String sessionId = session.getId();
        Serializer ser = Serializer.getSerializer();
        SocketMessage msg = ser.deserialize(message, SocketMessage.class);
        executorService.execute(() -> getMessageProcessor().processMessage(sessionId, msg.getMessageOperation(), msg.getMessageData()));
    }

    @OnClose
    public void onClose(CloseReason reason, Session session) {
        sessions.remove(session);
    }

    @OnError
    public void onError(Throwable cause, Session session) {
        log.info(cause.getMessage());
    }

    public void sendTo(String sessionId, Object object, Object operation)
    {
        SocketMessageGenerator generator = new SocketMessageGenerator();
        String msg = generator.generateMessageString(object, operation);
        sendToClient(getSessionFromId(sessionId), msg);
    }

    public Session getSessionFromId(String sessionId)
    {
        for(Session s : sessions)
        {
            if(s.getId().equals(sessionId))
                return s;
        }
        return null;
    }

    public void broadcast(Object object, Object operation)
    {
        for(Session session : sessions) {
            sendTo(session.getId(), object, operation);
        }
    }

    public void sendToOthers(String sessionId, Object object, Object operation)
    {
        Session session = getSessionFromId(sessionId);
        for(Session ses : sessions) {
            if(!ses.getId().equals(session.getId()))
                sendTo(ses.getId(), object, operation);
        }
    }

    private void sendToClient(Session session, String message)
    {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }
}
