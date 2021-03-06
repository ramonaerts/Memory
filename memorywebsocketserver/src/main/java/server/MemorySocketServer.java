package server;

import client.RestClient;
import interfaces.*;
import logic.MemoryLogic;
import messaging.ServerHandlerFactory;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;

public class MemorySocketServer {
    private static final int PORT = 8095;
    private static Logger log = Logger.getLogger(MemorySocketServer.class.getName());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        startWebSocketServer();
    }

    // Start the web socket server
    private static void startWebSocketServer() {

        IServerHandlerFactory factory = new ServerHandlerFactory();
        IServerMessageProcessor messageProcessor = new ServerMessageProcessor(factory);
        final ServerWebSocket socket = new ServerWebSocket();
        socket.setMessageProcessor(messageProcessor);

        IServerMessageGenerator messageGenerator = new ServerMessageGenerator(socket);

        IRestClient restClient = new RestClient();

        IGameLogic game = new MemoryLogic(messageGenerator, restClient);
        messageProcessor.registerGame(game);


        Server webSocketServer = new Server();
        ServerConnector connector = new ServerConnector(webSocketServer);
        connector.setPort(PORT);
        webSocketServer.addConnector(connector);

        // Setup the basic application "context" for this application at "/"
        // This is also known as the handler tree (in jetty speak)
        ServletContextHandler webSocketContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
        webSocketContext.setContextPath("/");
        webSocketServer.setHandler(webSocketContext);

        try {
            // Initialize javax.websocket layer
            ServerContainer wscontainer = WebSocketServerContainerInitializer.configureContext(webSocketContext);

            // Add WebSocket endpoint to javax.websocket layer

            ServerEndpointConfig config = ServerEndpointConfig.Builder.create(socket.getClass(), socket.getClass().getAnnotation(ServerEndpoint.class).value())
                    .configurator(new ServerEndpointConfig.Configurator() {
                        @Override
                        public <T> T getEndpointInstance(Class<T> endpointClass) {
                            return (T) socket;
                        }
                    })
                    .build();

            wscontainer.addEndpoint(config);

            webSocketServer.start();

            webSocketServer.join();
        } catch (Exception t) {
            log.info(t);
        }
    }
}
