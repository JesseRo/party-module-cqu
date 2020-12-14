//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dt.springjdbc.dao.impl;

import java.util.List;

public class QueryResult<T> {
    private List<T> list;
    private int totalRow;

    public QueryResult() {
    }

    public QueryResult(List<T> list, int totalRow) {
        this.list = list;
        this.totalRow = totalRow;
    }

    public List<T> getList() {
        return this.list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getTotalRow() {
        return this.totalRow;
    }

    public void setTotalRow(int totalRow) {
        this.totalRow = totalRow;
    }
}
