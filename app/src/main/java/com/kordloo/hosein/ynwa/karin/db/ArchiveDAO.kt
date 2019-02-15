package com.kordloo.hosein.ynwa.karin.db

import com.kordloo.hosein.ynwa.karin.model.Archive
import com.kordloo.hosein.ynwa.karin.model.Customer
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort

class ArchiveDAO {
    private var realm = Realm.getDefaultInstance()

    fun save(archive: Archive) {
        realm.executeTransaction {
            val nextId = if (archive.id == 0.toLong())
                autoIncrementId(it)
            else
                archive.id

            archive.id = nextId
            realm.copyToRealmOrUpdate(archive)
        }
    }

    private fun autoIncrementId (realm: Realm): Long {
        val currentId = realm.where(Archive::class.java).max("id")

        return if (currentId == null)
            1
        else
            currentId.toLong() + 1
    }

    fun findAll(): RealmResults<Archive> {
        return realm.where(Archive::class.java)
            .sort("id", Sort.DESCENDING)
            .findAll()
    }

    fun findByCustomerId(id: Long): RealmResults<Archive> {
        return realm.where(Archive::class.java)
            .equalTo("customerId", id)
            .sort("id", Sort.DESCENDING)
            .findAll()
    }

    fun close() {
        realm.close()
    }
}