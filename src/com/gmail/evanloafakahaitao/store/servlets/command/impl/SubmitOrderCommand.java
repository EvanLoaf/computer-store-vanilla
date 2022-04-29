package com.gmail.evanloafakahaitao.store.servlets.command.impl;

import com.gmail.evanloafakahaitao.store.config.ConfigurationManager;
import com.gmail.evanloafakahaitao.store.config.properties.PageProperties;
import com.gmail.evanloafakahaitao.store.dao.model.Item;
import com.gmail.evanloafakahaitao.store.dao.model.Order;
import com.gmail.evanloafakahaitao.store.dao.model.User;
import com.gmail.evanloafakahaitao.store.services.OrderService;
import com.gmail.evanloafakahaitao.store.services.impl.OrderServiceImpl;
import com.gmail.evanloafakahaitao.store.servlets.command.Command;
import com.gmail.evanloafakahaitao.store.servlets.model.CommandEnum;
import com.gmail.evanloafakahaitao.store.servlets.model.UserPrincipal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SubmitOrderCommand implements Command {

    private OrderService orderService = new OrderServiceImpl();
    private ConfigurationManager configurationManager = ConfigurationManager.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String vendorCode = request.getParameter("vendor_code");
        Integer quantity = Integer.parseInt(request.getParameter("quantity"));
        Item item = Item.newBuilder()
                .withVendorCode(vendorCode)
                .build();
        HttpSession session = request.getSession();
        UserPrincipal userPrincipal = (UserPrincipal) session.getAttribute("user");
        User user = User.newBuilder()
                .withId(userPrincipal.getId())
                .build();
        Order order = Order.newBuilder()
                .withUser(user)
                .withItem(item)
                .withQuantity(quantity)
                .build();
        int changedRows = orderService.save(order);
        if (changedRows == 1) {
            response.sendRedirect(request.getContextPath() + CommandEnum.ORDERS.getUrl());
        } else {
            request.setAttribute("error", "Could not save order");
            return configurationManager.getProperty(PageProperties.ITEMS_PAGE_PATH);
        }
        return null;
    }
}
