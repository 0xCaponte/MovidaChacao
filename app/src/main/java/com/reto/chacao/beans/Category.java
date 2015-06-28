package com.reto.chacao.beans;

/**
 * Created by ULISES HARRIS on 27/05/2015.
 */
public class Category extends AppBean {

    private int id;
    private String name;
    private boolean checked;

    public int getCategoryId() {
        return id;
    }

    public void setCategoryId(int categoryId) {
        this.id = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
