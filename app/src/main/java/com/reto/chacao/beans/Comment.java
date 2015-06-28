package com.reto.chacao.beans;

import java.util.Date;

/**
 * Created by ULISES HARRIS on 27/05/2015.
 */
public class Comment extends AppBean {

    private String body;
    private String commenterFirstName;
    private int commenterId;
    private String commenterLastName;
    private Date created;
    private String emoticons;
    private boolean hidden;
    private String image;
    private String link;
    private int comment_id;


    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCommenterFirstName() {
        return commenterFirstName;
    }

    public void setCommenterFirstName(String commenterFirstName) {
        this.commenterFirstName = commenterFirstName;
    }

    public int getCommenterId() {
        return commenterId;
    }

    public void setCommenterId(int commenterId) {
        this.commenterId = commenterId;
    }

    public String getCommenterLastName() {
        return commenterLastName;
    }

    public void setCommenterLastName(String commenterLastName) {
        this.commenterLastName = commenterLastName;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getEmoticons() {
        return emoticons;
    }

    public void setEmoticons(String emoticons) {
        this.emoticons = emoticons;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }
}
