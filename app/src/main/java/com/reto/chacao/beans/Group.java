package com.reto.chacao.beans;

import java.util.ArrayList;

/**
 * Created by ULISES HARRIS on 25/05/2015.
 */
public class Group extends AppBean {

    private int group_id;
    private String group_name;
    private String group_status;
    private String description;
    private String user_group_status;
    private int user_id;
    private ArrayList<roles_name> group_roles;
    private boolean checked;
    private String imageUrl;
    private int imageUrlDrawable;
    private ArrayList<Category> categories;


    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getGroup_status() {
        return group_status;
    }

    public void setGroup_status(String group_status) {
        this.group_status = group_status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUser_group_status() {
        return user_group_status;
    }

    public void setUser_group_status(String user_group_status) {
        this.user_group_status = user_group_status;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public ArrayList<roles_name> getGroup_roles() {
        return group_roles;
    }

    public void setGroup_roles(ArrayList<roles_name> group_roles) {
        this.group_roles = group_roles;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public class roles_name {

        public String group_roles_name;

        public String getGroup_roles_name() {
            return group_roles_name;
        }

        public void setGroup_roles_name(String group_roles_name) {
            this.group_roles_name = group_roles_name;
        }


    }

    public int getImageUrlDrawable() {
        return imageUrlDrawable;
    }

    public void setImageUrlDrawable(int imageUrlDrawable) {
        this.imageUrlDrawable = imageUrlDrawable;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }
}
