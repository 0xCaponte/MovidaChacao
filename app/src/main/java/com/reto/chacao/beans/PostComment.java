package com.reto.chacao.beans;

/**
 * Created by Eduardo Luttinger on 29/05/2015.
 */
public class PostComment extends AppBean {

     private static final String TAG = "POST_COMMENT_BEAN";
     private String commentProfileImageUrl;
     private String commentOrnerName;
     private String comment;
     private String commentDate;
     private String commentLikes;


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    public String getCommentLikes() {
        return commentLikes;
    }

    public void setCommentLikes(String commentLikes) {
        this.commentLikes = commentLikes;
    }

    public String getCommentProfileImageUrl() {
        return commentProfileImageUrl;
    }

    public void setCommentProfileImageUrl(String commentProfileImageUrl) {
        this.commentProfileImageUrl = commentProfileImageUrl;
    }

    public String getCommentOrnerName() {
        return commentOrnerName;
    }

    public void setCommentOrnerName(String commentOrnerName) {
        this.commentOrnerName = commentOrnerName;
    }


    @Override
    public String toString() {
        return TAG;
    }
}
