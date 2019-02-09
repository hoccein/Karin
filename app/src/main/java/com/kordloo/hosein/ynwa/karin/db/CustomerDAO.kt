package com.kordloo.hosein.ynwa.karin.db

import com.kordloo.hosein.ynwa.karin.model.Customer
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort

class CustomerDAO {
    private var realm = Realm.getDefaultInstance()

    fun save(customer: Customer) {
        realm.executeTransaction {
            val nextId = if (customer.id == 0.toLong())
                    autoIncrementId(it)
                else
                    customer.id

            customer.id = nextId
            realm.copyToRealmOrUpdate(customer)
        }
    }

    private fun autoIncrementId (realm: Realm): Long {
        val currentId = realm.where(Customer::class.java).max("id")

        return if (currentId == null)
            1
        else
            currentId.toLong() + 1
    }

    fun findAll(): RealmResults<Customer>? {
        return realm.where(Customer::class.java)/*.sort("id", Sort.DESCENDING)*/.findAll()
    }

    fun find(id: Long): Customer {
        return realm.where(Customer::class.java).equalTo("id", id).findFirst()!!
    }

    fun deleteAll() {
        realm.executeTransaction{
            findAll()?.deleteAllFromRealm()
        }
    }

    fun delete(id: Long) {
       realm.executeTransaction {
           find(id).deleteFromRealm()
       }
    }

    fun close() {
        realm.close()
    }

}