package com.dt.springjdbc.dao.impl;

import java.util.List;

public class PostgresqlQueryResult<T> {
    private List<T> list;
    private int totalPage;
    private int pageNow;

    public PostgresqlQueryResult(List<T> list, int totalPage, int pageNow) {
        this.list = list;
        this.totalPage = totalPage;
        this.pageNow = pageNow + 1;
    }

    public List<T> getList() {
        return this.list;
    }

    public int getPageNow() {
        return this.pageNow;
    }

    public int getTotalPage() {
        return this.totalPage;
    }
}
