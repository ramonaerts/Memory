package messages;

public class SocketMessage {
    private MessageOperation messageOperation;

    private String messageData;

    public SocketMessage(MessageOperation operation, String data)
    {
        this.messageOperation = operation;
        this.messageData = data;
    }

    public MessageOperation getMessageOperation()
    {
        return messageOperation;
    }

    public String getMessageData(){
        return messageData;
    }
}
