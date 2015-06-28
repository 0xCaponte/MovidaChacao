package com.reto.chacao.beans;

import java.util.ArrayList;

/**
 * Created by ULISES HARRIS on 09/06/2015.
 */
public class Conversation extends AppBean {
    private int id;
    private String subject;
    private ArrayList<UserProfile> users;
    private ArrayList<Messages> messages;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public ArrayList<UserProfile> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<UserProfile> users) {
        this.users = users;
    }

    public ArrayList<Messages> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Messages> messages) {
        this.messages = messages;
    }
}
