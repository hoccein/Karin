package com.kordloo.hosein.ynwa.karin.model

import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Ware(@PrimaryKey var id: Long, var price: String) : Parcelable, RealmObject(){

    constructor() : this(0, "")
}