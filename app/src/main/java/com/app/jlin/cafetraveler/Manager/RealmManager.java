package com.app.jlin.cafetraveler.Manager;

import android.content.Context;

import com.app.jlin.cafetraveler.Constants.Constants;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;

/**
 * Created by jennifer on 2018/3/29.
 */

public class RealmManager {

    private static RealmManager realmManager = new RealmManager();

    private String realmDBName;
    private RealmConfiguration config = null;

    /** 取回 realmManager instence */
    public synchronized static RealmManager getInstance(){return realmManager ;}

    private void initRealmAndConfiguration(String userName){
        realmDBName = String.format(Constants.REALM_NAME,userName);

        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name(realmDBName)
                .schemaVersion(Constants.REALM_VERSION)
                .migration(new CafeTravelerMigration())
                .build();

        if(null == config || !config.equals(configuration)){
            config = configuration;
            // Use the config
            Realm.setDefaultConfiguration(config);
        }
    }

    /** 取出 Realm */
    public Realm getRealm(){return Realm.getDefaultInstance(); }

    /** realm migration 更新版本 */
    class CafeTravelerMigration implements RealmMigration{
        @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

        }
        @Override
        public int hashCode() {
            return 37;
        }

        @Override
        public boolean equals(Object o) {
            return (o instanceof CafeTravelerMigration);
        }
    };
}
