package com.reto.chacao.beans;

/**
 * Created by Eduardo Luttinger on 28/05/2015.
 */
public class DetailPost extends AppBean {

    private static final String TAG = "DETAIL_POST_BEAN";
    private String postProfileImageUrl;
    private String postOwnerName;
    private String postDate;
    private String postOwnerComment;
    private int[] postImages;


    public String getPostProfileImageUrl() {
        return postProfileImageUrl;
    }

    public void setPostProfileImageUrl(String postProfileImageUrl) {
        this.postProfileImageUrl = postProfileImageUrl;
    }

    public String getPostOwnerName() {
        return postOwnerName;
    }

    public void setPostOwnerName(String postOwnerName) {
        this.postOwnerName = postOwnerName;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    @Override
    public String toString() {
        return TAG;
    }

    public String getPostOwnerComment() {
        return postOwnerComment;
    }

    public void setPostOwnerComment(String postOwnerComment) {
        this.postOwnerComment = postOwnerComment;
    }

    public int[] getPostImages() {
        return postImages;
    }

    public void setPostImages(int[] postImages) {
        this.postImages = postImages;
    }
}
