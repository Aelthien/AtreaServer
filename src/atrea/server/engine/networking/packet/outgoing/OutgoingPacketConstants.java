package atrea.server.engine.networking.packet;

public class OutgoingPacketConstants
{
    public static int LOG_OUT = 0;
    public static int LOGIN_RESPONSE = 1;
    public static int REGISTER_RESPONSE = 2;

    public static int CHAT_MESSAGE = 11;
    public static int OPEN_INTERFACE = 12;

    public static int SEND_POSITIONS = 19;
    public static int SEND_TRANSFORM = 20;
    public static int SEND_REGION = 21;

    public static int SPAWN_ENTITY = 28;
    public static int SEND_NAME = 29;
    public static int SEND_ENTITY_STATUS = 30;

    public static int SEND_ITEM_CONTAINER = 32;

    public static int UPDATE_ENTITIES = 40;
    public static int SEND_PING = 50;
    public static int ENTITY_CLICK = 51;
}