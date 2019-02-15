package com.kordloo.hosein.ynwa.karin.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ArchiveParc(
    var customerId: Long,
    var customerName: String,
    var customerPhone: String,
    var wares: List<WareArchiveParc>,
    var date: String,
    var totalWares: Int,
    var totalPrize: Int
) : Parcelable