package com.gmail.evanloafakahaitao.store.servlets.command.impl;

import com.gmail.evanloafakahaitao.store.config.ConfigurationManager;
import com.gmail.evanloafakahaitao.store.config.properties.PageProperties;
import com.gmail.evanloafakahaitao.store.dao.model.Item;
import com.gmail.evanloafakahaitao.store.services.ItemService;
import com.gmail.evanloafakahaitao.store.services.impl.ItemServiceImpl;
import com.gmail.evanloafakahaitao.store.servlets.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ItemsCommand implements Command {

    private ItemService itemService = new ItemServiceImpl();
    private ConfigurationManager configurationManager = ConfigurationManager.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Item> items = itemService.findAll();
        if (items != null) {
            request.setAttribute("items", items);
        } else {
            request.setAttribute("error", "Error retrieving items");
        }
        return configurationManager.getProperty(PageProperties.ITEMS_PAGE_PATH);
    }
}
