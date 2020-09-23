package System;

import obj.User;

public class State {
    private static State s;
    private User currentUser;

    private State () {

    }

    public static State getInstance() {
        if(s == null)
            s = new State();
        return s;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
