package atrea.server.engine.net.database;

public class NetworkOpcodes {
    public static int REGISTRATION_FAILED = 0;
    public static int REGISTRATION_SUCCESSFUL = 1;
    public static int USERNAME_TAKEN = 2;
    public static int EMAIL_TAKEN = 3;

    public static int INVALID_INFORMATION = 0;
    public static int LOGIN_SUCCESSFUL = 1;
    public static int ALREADY_LOGGED_IN = 2;
    public static int USER_BANNED = 3;
    public static int SERVER_ERROR = 4;
}