package com.cfd.messagefilter.models;

import io.realm.RealmObject;

/**
 * Created by Chirag on 06-02-2017.
 */

class SMS extends RealmObject {
    private int _id;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
