package messaging;

import interfaces.IServerMessageHandler;
import serialization.Serializer;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseServerMessageHandler<T> implements IServerMessageHandler {

    public void handleMessage(String data, String sessionId)
    {
        Serializer ser = Serializer.getSerializer();
        Type type = ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        T msg =  ser.deserialize(data, type);
        handleMessageInternal(msg, sessionId);
    }

    public abstract void handleMessageInternal(T message, String sessionId);
}
