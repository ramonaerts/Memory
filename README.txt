UI
SpecificController -> {specificmethod}getGameClient[a].specificmethod
GameClient -> {specificmethod}clientmessagegenerator.specificmethod
ClientMessageGenerator -> {specificmethod}.send(message)
ClientWebSocket -> {send}EncapsulatingMessageGenerator.generatemessagestring(object)
ClientWebSocket -> {send}SendMessageToServer(string message ^)
ServerWebsocket -> {onText}deserialize(string message ^)
ServerWebsocket -> {onText}processMessage(sessionId, msg.type^, msg.data^)
BaseMessageProcessor [a]
ServerMessageProcessor -> {processMessage}getmessagehandlerfactory.gethandler(data from processmessage)
ServerMessageProcessor -> {processMessage}handleMessage(data, sessionId)
BaseMessageHandler [a] -> {handleMessage}deserialize stuff -> handleMessageInternal(in correct messagehandler)
"Specific" ServerMessageHandler -> {handleMessageInternal}specific gamelogic method
GameLogic -> {specificmethod}serverMessagegenerator.specificmethod
ServerMessageGenerator -> {specificmethod}.sendTo||.broadcast||.sendToOthers
ServerWebsocket -> {sendToClient}.sendText(message) to specified session(s)
ClientWebSocket -> {onWebSocketText}.onWebSocketMessageReceived(message, sessionId)
ClientWebSocket -> {onWebSocketMessageReceived}deserialize(string message^)
ClientWebSocket -> {onWebSocketMessageReceived}processMessage(sessionId, msg.type^, msg.data^)
BaseMessageProcessor [a]
ClientMessageProcessor -> {processMessage}getmessagehandlerfactory.gethandler(data from processmessage)
ClientMessageProcessor -> {processMessage}handleMessage(data, sessionId)
BaseMessageHandler [a] -> {handleMessage}deserialize stuff -> handleMessageInternal(in correct messagehandler)
"Specific" ClientMessageHandler -> {handleMessageInternal}specific gameclient method
GameClient -> {specifichandle||processmethod}specific controller method
SpecificController -> {specificmethod}UImethod
UI