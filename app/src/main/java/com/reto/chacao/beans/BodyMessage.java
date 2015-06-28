package com.reto.chacao.beans;

/**
 * Created by ULISES HARRIS on 09/06/2015.
 */
public class BodyMessage extends AppBean {

    private String file;
    private String text;
    private String type;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
