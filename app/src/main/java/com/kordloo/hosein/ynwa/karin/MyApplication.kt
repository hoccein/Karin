package com.kordloo.hosein.ynwa.karin

import android.support.multidex.MultiDexApplication
import com.kordloo.hosein.ynwa.karin.db.MyMigration
import io.realm.Realm
import io.realm.RealmConfiguration

class MyApplication : MultiDexApplication() {

    companion object {
        lateinit var instance: MyApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        Realm.init(this)
        val realmConfig = RealmConfiguration.Builder()
            .name("mydb.realm")
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded()
//            .migration(MyMigration())
            .build()
        Realm.setDefaultConfiguration(realmConfig)
    }
}