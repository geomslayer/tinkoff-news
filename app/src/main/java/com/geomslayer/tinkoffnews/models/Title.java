package com.geomslayer.tinkoffnews.models;

import java.io.Serializable;
import java.util.Date;

public class Title implements Serializable {
    private long id;
    private String text;
    private Date pubDate;

    public Title(long id, String text, Date pubDate) {
        this.id = id;
        this.text = text;
        this.pubDate = pubDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }
}
