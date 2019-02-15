package com.kordloo.hosein.ynwa.karin.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Archive(
    @PrimaryKey
    var id: Long = 0,
    var customerId: Long,
    var customerName: String,
    var customerPhone: String,
    var customerAddress: String = "",
    var waresName: RealmList<String>,
    var waresPrice: RealmList<String>,
    var waresCount: RealmList<Int>,
    var date: String,
    var totalWares: Int,
    var totalPrize: Int
) : RealmObject() {
    constructor(): this(customerId = 0, customerName = "", customerPhone = "",
        waresName = RealmList(), waresCount = RealmList(),waresPrice = RealmList(),
        date = "", totalPrize = 0, totalWares = 0)
}