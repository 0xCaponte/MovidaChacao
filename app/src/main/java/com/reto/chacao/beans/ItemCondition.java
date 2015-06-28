package com.reto.chacao.beans;

import java.util.ArrayList;

/**
 * Created by Eduardo Luttinger on 11/06/2015.
 */
public class ItemCondition extends AppBean {

    private int id;
    private String name;
    private boolean checked;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public static ArrayList<ItemCondition> setItemCondition() {
        ArrayList<ItemCondition> itemConditions = new ArrayList<ItemCondition>();
        ItemCondition itemCondition = new ItemCondition();
        itemCondition.setId(0);
        itemCondition.setName("Brand New");
        itemConditions.add(itemCondition);
        ItemCondition itemConditionLike = new ItemCondition();
        itemConditionLike.setId(1);
        itemConditionLike.setName("Like New");
        itemConditions.add(itemConditionLike);
        ItemCondition itemConditionVery = new ItemCondition();
        itemConditionVery.setId(2);
        itemConditionVery.setName("Very Good");
        itemConditions.add(itemConditionVery);
        ItemCondition itemConditionGood = new ItemCondition();
        itemConditionGood.setId(3);
        itemConditionGood.setName("Good");
        itemConditions.add(itemConditionGood);

        return itemConditions;
    }
}
