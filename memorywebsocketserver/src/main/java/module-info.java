module memorywebsocketserver {
    exports messaging;
    requires gson;
    requires org.eclipse.jetty.servlet;
    requires org.eclipse.jetty.server;
    requires org.eclipse.jetty.websocket.javax.websocket.server;
    requires javax.websocket.api;
}