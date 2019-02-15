package com.kordloo.hosein.ynwa.karin.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WareArchiveParc(
    val wareName: String,
    val warePrice: String,
    val wareCount: Int
) : Parcelable