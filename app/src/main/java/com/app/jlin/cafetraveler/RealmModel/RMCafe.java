package com.app.jlin.cafetraveler.RealmModel;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by jennifer on 2018/3/29.
 */

public class RMCafe extends RealmObject {
    @PrimaryKey
    private String id;
}
