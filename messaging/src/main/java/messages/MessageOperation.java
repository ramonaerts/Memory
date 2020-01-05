package messages;

public enum MessageOperation {

    //to server
    PLAYERLOGIN,
    PLAYERSTARTGAME,
    PLAYERJOINGAME,
    TURNCARD,

    //to client
    PLAYERLOGINRESULT,
    UPDATELOBBY,
    STARTGAMERESULT,
    JOINGAMERESULT,
    PLAYERJOINSGAME,

}