package com.li.yun.biao.common.dao;


import java.util.Collections;
import java.util.List;

public class Page<T> extends Pagination {
    private static final long serialVersionUID = 1L;
    private List<T> records = Collections.emptyList();
    private String orderByField;
    private boolean isAsc = true;

    public Page() {
    }

    public Page(int current, int size) {
        super(current, size);
    }

    public Page(int current, int size, String orderByField) {
        super(current, size);
        this.setOrderByField(orderByField);
    }

    public List<T> getRecords() {
        return this.records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public String getOrderByField() {
        return this.orderByField;
    }

    public void setOrderByField(String orderByField) {
        if (null != orderByField && "" != orderByField) {
            this.orderByField = orderByField;
        }

    }

    public boolean isAsc() {
        return this.isAsc;
    }

    public void setAsc(boolean isAsc) {
        this.isAsc = isAsc;
    }

    public String toString() {
        StringBuffer pg = new StringBuffer();
        pg.append(" Page:{ [").append(super.toString()).append("], ");
        if (this.records != null) {
            pg.append("records-size:").append(this.records.size());
        } else {
            pg.append("records is null");
        }

        return pg.append(" }").toString();
    }
}
