package com.gmail.evanloafakahaitao.store.servlets.util;

import com.gmail.evanloafakahaitao.store.dao.model.User;
import com.gmail.evanloafakahaitao.store.servlets.model.UserPrincipal;

public class UserPrincipalConverter {

    public UserPrincipal toUserPrincipal(User user) {
        return UserPrincipal.newBuilder()
                .withId(user.getId())
                .withName(user.getFirstName() + " " + user.getLastName())
                .withEmail(user.getEmail())
                .withRole(user.getRole())
                .build();
    }
}
