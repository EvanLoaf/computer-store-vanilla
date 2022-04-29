package com.gmail.evanloafakahaitao.store.servlets;

import com.gmail.evanloafakahaitao.store.servlets.command.Command;
import com.gmail.evanloafakahaitao.store.servlets.command.impl.ItemsCommand;
import com.gmail.evanloafakahaitao.store.servlets.command.impl.LoadXmlItemsCommand;
import com.gmail.evanloafakahaitao.store.servlets.command.impl.LoginCommand;
import com.gmail.evanloafakahaitao.store.servlets.command.impl.UsersCommand;
import com.gmail.evanloafakahaitao.store.servlets.model.CommandEnum;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {

    private Map<CommandEnum, Command> commands = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String command = req.getParameter("command");
        Command commandAction = null;
        try {
            commandAction = commands.get(CommandEnum.getCommand(command));
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal command value");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        if (commandAction != null) {
            try {
                String page = commandAction.execute(req, resp);
                if (page != null) {
                    getServletContext().getRequestDispatcher(page).forward(req, resp);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.printf("Command %s does not exist", command);
        }
    }

    @Override
    public void destroy() {
        System.out.println("Dispatcher Servlet destroyed");
        super.destroy();
    }

    @Override
    public void init() throws ServletException {
        System.out.println("Dispatcher Servlet initialized");
        commands.putIfAbsent(CommandEnum.LOGIN, new LoginCommand());
        commands.putIfAbsent(CommandEnum.USERS, new UsersCommand());
        commands.putIfAbsent(CommandEnum.ITEMS, new ItemsCommand());
        commands.putIfAbsent(CommandEnum.LOAD_ITEMS, new LoadXmlItemsCommand());
        super.init();
    }
}
