package com.shoppinggate.Model


import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class AllProducts_Response(
    @SerializedName("data")
    val `data`: List<AllProducts_Model>,
    @SerializedName("error")
    val error: String,
    @SerializedName("status")
    val status: Boolean
): Parcelable {
    @SuppressLint("ParcelCreator")
    @Parcelize
    data class AllProducts_Model(
        @SerializedName("brand_name")
        val brandName: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("image")
        val image: String,
        @SerializedName("image_guide")
        val imageGuide: String,
        @SerializedName("price_general")
        val priceGeneral: String,
        @SerializedName("rates")
        val rates: Int,
        @SerializedName("sale_price")
        val salePrice: String,
        @SerializedName("short_description")
        val shortDescription: String,
        @SerializedName("title")
        val title: String
    ) : Parcelable
}