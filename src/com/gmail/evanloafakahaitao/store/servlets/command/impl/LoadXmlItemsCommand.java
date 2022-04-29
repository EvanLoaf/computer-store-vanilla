package com.gmail.evanloafakahaitao.store.servlets.command.impl;

import com.gmail.evanloafakahaitao.store.config.ConfigurationManager;
import com.gmail.evanloafakahaitao.store.config.properties.FileProperties;
import com.gmail.evanloafakahaitao.store.config.properties.PageProperties;
import com.gmail.evanloafakahaitao.store.services.ItemService;
import com.gmail.evanloafakahaitao.store.services.XmlService;
import com.gmail.evanloafakahaitao.store.services.impl.ItemServiceImpl;
import com.gmail.evanloafakahaitao.store.services.impl.XmlServiceImpl;
import com.gmail.evanloafakahaitao.store.services.model.ItemXmlBinding;
import com.gmail.evanloafakahaitao.store.servlets.command.Command;
import com.gmail.evanloafakahaitao.store.servlets.model.CommandEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class LoadXmlItemsCommand implements Command {

    private ItemService itemService = new ItemServiceImpl();
    private XmlService xmlService = new XmlServiceImpl();
    private ConfigurationManager configurationManager = ConfigurationManager.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<ItemXmlBinding> items = xmlService.getItems(
                configurationManager.getProperty(FileProperties.XML_FILE_PATH),
                configurationManager.getProperty(FileProperties.SCHEMA_FILE_PATH)
        );
        int savedItems = itemService.save(items);
        if (savedItems == 0) {
            request.setAttribute("error", "No new items saved");
            return configurationManager.getProperty(PageProperties.USERS_PAGE_PATH);
        } else {
            response.sendRedirect(request.getContextPath() + CommandEnum.ITEMS.getUrl());
        }
        return null;
    }
}
