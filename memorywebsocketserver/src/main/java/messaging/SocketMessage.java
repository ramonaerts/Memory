package messaging;

public class SocketMessage {
    private String messageType;

    private String messageData;

    public SocketMessage(String type, String data)
    {
        this.messageType = type;
        this.messageData = data;
    }

    public String getMessageType()
    {
        return messageType;
    }

    public String getMessageData(){
        return messageData;
    }
}
