package com.gmail.evanloafakahaitao.store.services;

import com.gmail.evanloafakahaitao.store.dao.model.Item;
import com.gmail.evanloafakahaitao.store.services.model.ItemXmlBinding;

import java.util.List;

public interface ItemService {

    int save(List<ItemXmlBinding> itemXmlBindings);

    List<Item> findAll();

    Item findByVendorCode(Long vendorCode);
}
