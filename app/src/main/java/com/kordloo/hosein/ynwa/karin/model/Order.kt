package com.kordloo.hosein.ynwa.karin.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Order(var Ware: Ware, var count: Int = 0) : Parcelable {
    constructor(wareName: String, warePrice: String, count: Int) : this(Ware(name = wareName, price = warePrice ), count)
}