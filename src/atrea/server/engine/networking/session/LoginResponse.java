package atrea.server.networking.session;

import lombok.Getter;
import lombok.Setter;

public class LoginResponse {
    private @Getter @Setter int code;
    private @Getter @Setter int userId = -1;
}