//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dt.springjdbc.dao.impl;

import java.io.Serializable;
import java.util.Collection;

public interface BaseDao<T> {
    int addEntity(T var1);

    int updateEntity(T var1);

    int deleteEntity(Serializable var1);

    T getEntityById(Serializable var1);

    Collection<T> getAllEntitys();
}
