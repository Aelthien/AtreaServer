package atrea.server.engine.networking.session;

import atrea.server.engine.accounts.Account;
import lombok.Getter;
import lombok.Setter;

public class LoginResponse {
    private @Getter @Setter int code;
    private @Getter @Setter int userId = -1;
    private @Getter @Setter Account account;
}