package com.gmail.evanloafakahaitao.store.servlets.command.impl;

import com.gmail.evanloafakahaitao.store.config.ConfigurationManager;
import com.gmail.evanloafakahaitao.store.config.properties.PageProperties;
import com.gmail.evanloafakahaitao.store.dao.model.RoleEnum;
import com.gmail.evanloafakahaitao.store.dao.model.User;
import com.gmail.evanloafakahaitao.store.services.UserService;
import com.gmail.evanloafakahaitao.store.services.impl.UserServiceImpl;
import com.gmail.evanloafakahaitao.store.servlets.command.Command;
import com.gmail.evanloafakahaitao.store.servlets.model.CommandEnum;
import com.gmail.evanloafakahaitao.store.servlets.util.LoginValidator;
import com.gmail.evanloafakahaitao.store.servlets.util.UserPrincipalConverter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginCommand implements Command {

    private UserService userService = new UserServiceImpl();
    private ConfigurationManager configurationManager = ConfigurationManager.getInstance();
    private LoginValidator loginValidator = new LoginValidator();
    private UserPrincipalConverter userPrincipalConverter = new UserPrincipalConverter();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        boolean isValid = loginValidator.validate(email, password);
        if (isValid) {
            User user = userService.findByEmail(email.trim());
            HttpSession session = request.getSession();
            session.setAttribute("user", userPrincipalConverter.toUserPrincipal(user));
            if (user.getRole() == RoleEnum.ADMIN) {
                response.sendRedirect(request.getContextPath() + CommandEnum.USERS.getUrl());
            } else if (user.getRole() == RoleEnum.USER) {
                response.sendRedirect(request.getContextPath() + CommandEnum.ITEMS.getUrl());
            }
        } else {
            request.setAttribute("error", "Invalid email or password");
            request.setAttribute("email", email);
            request.setAttribute("password", password);
            return configurationManager.getProperty(PageProperties.LOGIN_PAGE_PATH);
        }
        return configurationManager.getProperty(PageProperties.LOGIN_PAGE_PATH);
    }
}
