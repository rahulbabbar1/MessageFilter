package com.cfd.messagefilter.models;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Chirag on 06-02-2017.
 */

public class SMSCategory extends RealmObject {
    private int categoryId;
    private String categoryName;
    private RealmList<SMS> smss;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public RealmList<SMS> getSmss() {
        return smss;
    }

    public void setSmss(RealmList<SMS> smss) {
        this.smss = smss;
    }

}
