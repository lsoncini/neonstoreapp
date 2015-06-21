package store;

import model.Session;

public interface SessionListener {
    void onLogin(Session session);
    void onLogout();
}
