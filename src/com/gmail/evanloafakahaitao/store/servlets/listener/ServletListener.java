package com.gmail.evanloafakahaitao.store.servlets.listener;

import com.gmail.evanloafakahaitao.store.config.ConfigurationManager;
import com.gmail.evanloafakahaitao.store.config.properties.FileProperties;
import com.gmail.evanloafakahaitao.store.dao.connection.ConnectionService;
import com.gmail.evanloafakahaitao.store.services.DatabaseBootService;
import com.gmail.evanloafakahaitao.store.services.impl.DatabaseBootServiceImpl;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServletListener implements ServletContextListener {

    private DatabaseBootService databaseBootService = new DatabaseBootServiceImpl();
    private ConfigurationManager configurationManager = ConfigurationManager.getInstance();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("App start-up, trying to run DB boot file");
        databaseBootService.executeBootFile(configurationManager.getProperty(FileProperties.SQL_BOOT_FILE_PATH));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionService.getInstance().closeConnection();
        System.out.println("App shutdown");
    }
}
