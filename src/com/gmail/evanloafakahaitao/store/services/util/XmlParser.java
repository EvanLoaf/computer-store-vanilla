package com.gmail.evanloafakahaitao.store.services.util;

import com.gmail.evanloafakahaitao.store.services.model.ItemsXmlBinding;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XmlParser {

    public ItemsXmlBinding unmarshal(File xml) {
        ItemsXmlBinding items = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ItemsXmlBinding.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            items = (ItemsXmlBinding) unmarshaller.unmarshal(xml);
        } catch (JAXBException e) {
            System.out.println("Error parsing XML");
            e.printStackTrace();
        }
        return items;
    }
}
