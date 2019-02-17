package com.winsonmac.ewallettest.apis

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ListAPIPayment {

    @FormUrlEncoded
    @POST(APIConfig.API_CREATE_PAYMENT)
    fun createPayment(@Field(APIConfig.KEY_OTP) otp: String): Call<JsonElement>

}