package com.kordloo.hosein.ynwa.karin.db

import com.kordloo.hosein.ynwa.karin.model.Ware
import io.realm.Realm
import io.realm.RealmResults

class WareDAO {
    private val realm = Realm.getDefaultInstance()

    fun save(ware: Ware) {
        realm.executeTransaction {
            if (ware.id != 0.toLong())
                ware.id = autoIncrementId(it)
            else
                ware.id = ware.id
            realm.copyToRealmOrUpdate(ware)
        }
    }

    private fun autoIncrementId (realm: Realm): Long {
        val currentId = realm.where(Ware::class.java).max("id")

        return if (currentId == null)
            1
        else
            currentId.toLong() + 1
    }

    fun findAll(): RealmResults<Ware>? {
        return realm.where(Ware::class.java).findAll()
    }

    fun deleteAll() {
        realm.executeTransaction{
            findAll()?.deleteAllFromRealm()
        }
    }

    fun close() {
        realm.close()
    }
}