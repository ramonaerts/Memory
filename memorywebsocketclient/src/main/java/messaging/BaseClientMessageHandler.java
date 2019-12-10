package messaging;

import serialization.Serializer;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseClientMessageHandler<T> implements IClientMessageHandler {

    public void handleMessage(String data, String sessionId)
    {
        Serializer ser = Serializer.getSerializer();;
        Type type = ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        T msg =  ser.deserialize(data, type);
        handleMessageInternal(msg, sessionId);
    }

    public abstract void handleMessageInternal(T message, String sessionId);
}
