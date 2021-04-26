package com.linhnv.diary.models.bos;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;
import java.util.Set;

public class FilterArticle {
    private String subject;
    private int feeling;
    private ZonedDateTime startAt;
    private int page;
    private HttpServletRequest request;
    private String userId;

    public FilterArticle() {
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getFeeling() {
        return feeling;
    }

    public void setFeeling(int feeling) {
        this.feeling = feeling;
    }

    public ZonedDateTime getStartAt() {
        return startAt;
    }

    public void setStartAt(ZonedDateTime startAt) {
        this.startAt = startAt;
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
