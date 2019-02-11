package com.kordloo.hosein.ynwa.karin.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FinalOrder(
    val customer: Customer,
    val orderList: ArrayList<Order>,
    val date: String,
    val totalPrize: Int,
    val totalWares: Int) : Parcelable {

    constructor() : this(Customer(), arrayListOf(), "",0,0)
}