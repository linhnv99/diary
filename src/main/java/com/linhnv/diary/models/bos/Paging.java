package com.linhnv.diary.models.bos;

public class Paging<T> {
    private int size;
    private int page;
    private int totalPage;
    private long totalRecord;
    private int totalInPage;
    private T data;

    public Paging() {
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(long totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getTotalInPage() {
        return totalInPage;
    }

    public void setTotalInPage(int totalInPage) {
        this.totalInPage = totalInPage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
