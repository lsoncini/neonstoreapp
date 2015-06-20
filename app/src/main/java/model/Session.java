package model;

public class Session extends Model {
    public String authenticationToken;
    public Account account;

    public Session(String authenticationToken, Account account) {
        this.authenticationToken = authenticationToken;
        this.account = account;
    }
}
