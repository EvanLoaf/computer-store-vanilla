package com.gmail.evanloafakahaitao.store.services.impl;

import com.gmail.evanloafakahaitao.store.dao.XmlDao;
import com.gmail.evanloafakahaitao.store.dao.impl.XmlDaoImpl;
import com.gmail.evanloafakahaitao.store.services.XmlService;
import com.gmail.evanloafakahaitao.store.services.model.ItemXmlBinding;
import com.gmail.evanloafakahaitao.store.services.model.ItemsXmlBinding;
import com.gmail.evanloafakahaitao.store.services.util.XmlParser;
import com.gmail.evanloafakahaitao.store.services.util.XmlValidator;

import java.io.File;
import java.util.List;

public class XmlServiceImpl implements XmlService {

    private XmlDao xmlDao = new XmlDaoImpl();
    private XmlValidator xmlValidator = new XmlValidator();
    private XmlParser xmlParser = new XmlParser();

    @Override
    public List<ItemXmlBinding> getItems(String filePath, String schemaPath) {
        ItemsXmlBinding items = new ItemsXmlBinding();
        File xml = xmlDao.getFile(filePath);
        File schema = xmlDao.getFile(schemaPath);
        boolean isValid = xmlValidator.validate(xml, schema);
        System.out.printf("XML file is valid : %s%n", isValid);
        if (isValid) {
            System.out.println("Parsing XML file...");
            items = xmlParser.unmarshal(xml);
        }
        return items.getItems();
    }
}
