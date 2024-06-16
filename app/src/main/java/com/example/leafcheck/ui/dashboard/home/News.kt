package com.example.leafcheck.ui.dashboard.home

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class News(
    val name: String,
    val description: String,
    val photo: Int
) : Parcelable