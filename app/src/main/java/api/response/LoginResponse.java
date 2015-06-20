package api.response;

import model.Account;
import model.Session;

public class LoginResponse extends APIResponse {
    public String authenticationToken;
    public Account account;

    public Session toSession() {
        return new Session(authenticationToken, account);
    }
}
