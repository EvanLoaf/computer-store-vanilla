package com.gmail.evanloafakahaitao.store.services;

import com.gmail.evanloafakahaitao.store.services.model.ItemXmlBinding;

import java.util.List;

public interface XmlService {

    List<ItemXmlBinding> getItems(String filePath, String schemaPath);
}
