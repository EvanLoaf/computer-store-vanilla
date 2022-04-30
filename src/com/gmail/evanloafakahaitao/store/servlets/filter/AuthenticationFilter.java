package com.gmail.evanloafakahaitao.store.servlets.filter;

import com.gmail.evanloafakahaitao.store.config.ConfigurationManager;
import com.gmail.evanloafakahaitao.store.config.properties.PageProperties;
import com.gmail.evanloafakahaitao.store.dao.model.RoleEnum;
import com.gmail.evanloafakahaitao.store.servlets.model.AccessMode;
import com.gmail.evanloafakahaitao.store.servlets.model.CommandEnum;
import com.gmail.evanloafakahaitao.store.servlets.model.RequestMethodEnum;
import com.gmail.evanloafakahaitao.store.servlets.model.UserPrincipal;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class AuthenticationFilter implements Filter {

    private static final ConfigurationManager CONFIGURATION_MANAGER = ConfigurationManager.getInstance();
    private static final Set<AccessMode> ADMIN_AVAILABLE = new HashSet<>();
    private static final Set<AccessMode> USER_AVAILABLE = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Authentication Filter initialized");

        /**
         * AccessMode - combination of parameters
         * In order to add protection to sensitive data
         * Depending on user's role, request type and command
         * Access might or might not be granted by Authenticator
         */

        // *** USER AVAILABLE ACCESS MODES ***
        USER_AVAILABLE.add(
                AccessMode.newBuilder()
                        .withRole(RoleEnum.USER)
                        .withCommand(CommandEnum.LOGIN)
                        .withRequest(RequestMethodEnum.POST)
                        .build()
        );
        USER_AVAILABLE.add(
                AccessMode.newBuilder()
                        .withRole(RoleEnum.USER)
                        .withCommand(CommandEnum.ITEMS)
                        .withRequest(RequestMethodEnum.GET)
                        .build()
        );
        USER_AVAILABLE.add(
                AccessMode.newBuilder()
                        .withRole(RoleEnum.USER)
                        .withCommand(CommandEnum.MAKE_ORDER)
                        .withRequest(RequestMethodEnum.GET)
                        .build()
        );
        USER_AVAILABLE.add(
                AccessMode.newBuilder()
                        .withRole(RoleEnum.USER)
                        .withCommand(CommandEnum.SUBMIT_ORDER)
                        .withRequest(RequestMethodEnum.POST)
                        .build()
        );
        USER_AVAILABLE.add(
                AccessMode.newBuilder()
                        .withRole(RoleEnum.USER)
                        .withCommand(CommandEnum.ORDERS)
                        .withRequest(RequestMethodEnum.GET)
                        .build()
        );
        USER_AVAILABLE.add(
                AccessMode.newBuilder()
                        .withRole(RoleEnum.USER)
                        .withCommand(CommandEnum.DELETE_ORDER)
                        .withRequest(RequestMethodEnum.GET)
                        .build()
        );



        // *** ADMIN AVAILABLE ACCESS MODES ***
        ADMIN_AVAILABLE.add(
                AccessMode.newBuilder()
                        .withRole(RoleEnum.ADMIN)
                        .withCommand(CommandEnum.LOGIN)
                        .withRequest(RequestMethodEnum.POST)
                        .build()
        );
        ADMIN_AVAILABLE.add(
                AccessMode.newBuilder()
                        .withRole(RoleEnum.ADMIN)
                        .withCommand(CommandEnum.USERS)
                        .withRequest(RequestMethodEnum.GET)
                        .build()
        );
        ADMIN_AVAILABLE.add(
                AccessMode.newBuilder()
                        .withRole(RoleEnum.ADMIN)
                        .withCommand(CommandEnum.ITEMS)
                        .withRequest(RequestMethodEnum.GET)
                        .build()
        );
        ADMIN_AVAILABLE.add(
                AccessMode.newBuilder()
                        .withRole(RoleEnum.ADMIN)
                        .withCommand(CommandEnum.LOAD_ITEMS)
                        .withRequest(RequestMethodEnum.GET)
                        .build()
        );
        ADMIN_AVAILABLE.add(
                AccessMode.newBuilder()
                        .withRole(RoleEnum.ADMIN)
                        .withCommand(CommandEnum.UPDATE_USER_MENU)
                        .withRequest(RequestMethodEnum.GET)
                        .build()
        );
        ADMIN_AVAILABLE.add(
                AccessMode.newBuilder()
                        .withRole(RoleEnum.ADMIN)
                        .withCommand(CommandEnum.UPDATE_USER)
                        .withRequest(RequestMethodEnum.POST)
                        .build()
        );

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        String command = request.getParameter("command");
        String requestMethod = request.getMethod();
        if (session == null) {
            defaultRequest(servletRequest, servletResponse, filterChain, request, response, command);
        } else {
            UserPrincipal userPrincipal = (UserPrincipal) session.getAttribute("user");
            if (userPrincipal == null) {
                defaultRequest(servletRequest, servletResponse, filterChain, request, response, command);
            } else {
                CommandEnum commandEnum = CommandEnum.getCommand(command);
                //TODO Error handling showcase, remove if ever in production LOL
                if (commandEnum == null) {
                    throw new ServletException("Test Error - AuthenticationFilter.doFilter");
                }
                RoleEnum roleEnum = userPrincipal.getRole();
                RequestMethodEnum requestMethodEnum = RequestMethodEnum.getRequest(requestMethod);
                AccessMode accessMode = AccessMode.newBuilder()
                        .withCommand(commandEnum)
                        .withRole(roleEnum)
                        .withRequest(requestMethodEnum)
                        .build();
                if (!USER_AVAILABLE.contains(accessMode) && !ADMIN_AVAILABLE.contains(accessMode)) {
                    session.removeAttribute("user");
                    response.sendRedirect(request.getContextPath() + CONFIGURATION_MANAGER.getProperty(PageProperties.LOGIN_PAGE_PATH));
                } else {
                    filterChain.doFilter(servletRequest, servletResponse);
                }
            }
        }
    }

    private void defaultRequest(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain,
            HttpServletRequest request,
            HttpServletResponse response,
            String command
    ) throws IOException, ServletException {
        if (request.getMethod().equals("POST")) {
            if (CommandEnum.getCommand(command) == CommandEnum.LOGIN) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                response.sendRedirect(request.getContextPath() + CONFIGURATION_MANAGER.getProperty(PageProperties.LOGIN_PAGE_PATH));
            }
        } else {
            response.sendRedirect(request.getContextPath() + CONFIGURATION_MANAGER.getProperty(PageProperties.LOGIN_PAGE_PATH));
        }
    }

    @Override
    public void destroy() {
        System.out.println("Authentication Filter destroyed");
    }
}
