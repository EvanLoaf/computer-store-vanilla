package com.gmail.evanloafakahaitao.store.servlets.util;

import com.gmail.evanloafakahaitao.store.dao.model.User;
import com.gmail.evanloafakahaitao.store.services.UserService;
import com.gmail.evanloafakahaitao.store.services.impl.UserServiceImpl;

public class LoginValidator {

    private UserService userService = new UserServiceImpl();

    public boolean validate(String email, String password) {
        if (email != null && !email.equals("") && password != null && !password.equals("")) {
            User user = userService.findByEmail(email.trim());
            if (user != null) {
                return user.getPassword().equals(password.trim());
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
