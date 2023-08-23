package com.phucthinh.appbookingfood.model.Event;

import com.phucthinh.appbookingfood.model.User;

public class EditRemoveUserEvent {
    User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public EditRemoveUserEvent(User user) {
        this.user = user;
    }
}
