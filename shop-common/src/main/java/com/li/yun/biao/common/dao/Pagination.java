package com.li.yun.biao.common.dao;


import org.apache.ibatis.session.RowBounds;

import java.io.Serializable;

public class Pagination extends RowBounds implements Serializable {
    private static final long serialVersionUID = 1L;
    private int total;
    private int size;
    private int pages;
    private int current;
    private boolean searchCount;

    public Pagination() {
        this.current = 1;
        this.searchCount = true;
    }

    public Pagination(int current, int size) {
        this(current, size, true);
    }

    public Pagination(int current, int size, boolean searchCount) {
        super(offsetCurrent(current, size), size);
        this.current = 1;
        this.searchCount = true;
        if (current > 1) {
            this.current = current;
        }

        this.size = size;
        this.searchCount = searchCount;
    }

    protected static int offsetCurrent(int current, int size) {
        return current > 0 ? (current - 1) * size : 0;
    }

    public int getOffsetCurrent() {
        return offsetCurrent(this.current, this.size);
    }

    public boolean hasPrevious() {
        return this.current > 1;
    }

    public boolean hasNext() {
        return this.current < this.pages;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
        this.pages = this.total / this.size;
        if (this.total % this.size != 0) {
            ++this.pages;
        }

    }

    public int getSize() {
        return this.size;
    }

    public int getPages() {
        return this.pages;
    }

    public int getCurrent() {
        return this.current;
    }

    public boolean isSearchCount() {
        return this.searchCount;
    }

    public void setSearchCount(boolean searchCount) {
        this.searchCount = searchCount;
    }

    public String toString() {
        return "Pagination { total=" + this.total + " ,size=" + this.size + " ,pages=" + this.pages + " ,current=" + this.current + " }";
    }
}