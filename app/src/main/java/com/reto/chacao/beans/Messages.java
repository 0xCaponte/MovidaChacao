package com.reto.chacao.beans;

import java.util.Date;

/**
 * Created by ULISES HARRIS on 09/06/2015.
 */
public class Messages extends AppBean {

    private int _id;
    private BodyMessage bodyMessage;
    private Date created;
    private String from;
    private String modified;
    private String read;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public BodyMessage getBodyMessage() {
        return bodyMessage;
    }

    public void setBodyMessage(BodyMessage bodyMessage) {
        this.bodyMessage = bodyMessage;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }
}
