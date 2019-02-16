package com.kordloo.hosein.ynwa.karin.db

import com.kordloo.hosein.ynwa.karin.model.Ware
import io.realm.Realm
import io.realm.RealmResults

class WareDAO {
    private val realm = Realm.getDefaultInstance()

    fun save(ware: Ware) {
        realm.executeTransaction {
            val nextId = if (ware.id == 0.toLong())
                autoIncrementId(it)
            else
                ware.id
            ware.id = nextId
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

    fun delete(id: Long) {
        realm.executeTransaction {
            it.where(Ware::class.java).equalTo("id", id).findFirst()?.deleteFromRealm()
        }
    }

    fun close() {
        realm.close()
    }
}