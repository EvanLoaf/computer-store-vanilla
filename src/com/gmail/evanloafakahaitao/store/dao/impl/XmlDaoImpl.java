package com.gmail.evanloafakahaitao.store.dao.impl;

import com.gmail.evanloafakahaitao.store.dao.XmlDao;

import java.io.File;

public class XmlDaoImpl implements XmlDao {

    @Override
    public File getFile(String path) {
        return new File(path);
    }
}
