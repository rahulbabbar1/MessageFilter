package com.cfd.messagefilter.models;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Chirag on 06-02-2017.
 */

public class SMSCategory extends RealmObject {
    private int id;
    private String name;
    private RealmList<SMS> smss;

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

    public RealmList<SMS> getSmss() {
        return smss;
    }

    public void setSmss(RealmList<SMS> smss) {
        this.smss = smss;
    }

}
