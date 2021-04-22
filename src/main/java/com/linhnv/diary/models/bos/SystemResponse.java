package com.linhnv.diary.models.bos;

public class SystemResponse<T> {

    private int status;

    private String error;

    private T data;

    public SystemResponse() {
    }

    public SystemResponse(int status, String message) {
        this.status = status;
        this.error = message;
    }

    public SystemResponse(int status, String message, T data) {
        this.status = status;
        this.error = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
