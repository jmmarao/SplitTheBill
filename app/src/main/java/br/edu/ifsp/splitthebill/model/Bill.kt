package br.edu.ifsp.splitthebill.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Bill(
    val id: Int,
    var name: String,
    var product: String,
    var amount: Double
) : Parcelable
