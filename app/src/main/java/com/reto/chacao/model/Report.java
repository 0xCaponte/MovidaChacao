package com.reto.chacao.model;

/**
 * Created by gustavo on 25/06/15.
 */
public class Report {
    int id;
    String text;

    public Report() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
