package com.kordloo.hosein.ynwa.karin.model

import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Ware(@PrimaryKey var id: Long = 0, var name: String, var price: String, var path: String = "")
    : Parcelable, RealmObject(){

    constructor() : this(0, "", "", "")
}