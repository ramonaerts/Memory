package messages;

import serialization.Serializer;

public class SocketMessageGenerator {
    Serializer ser = Serializer.getSerializer();

    public String generateMessageString(Object content, Object operation)
    {
        MessageOperation messageOperation = (MessageOperation) operation;
        SocketMessage msg = generateMessage(content, messageOperation);
        return ser.serialize(msg);
    }

    public SocketMessage generateMessage(Object content, MessageOperation messageOperation)
    {
        String messageForServerJson = ser.serialize(content);
        return new SocketMessage(messageOperation, messageForServerJson);
    }
}
