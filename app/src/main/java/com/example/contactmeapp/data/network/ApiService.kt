package com.example.contactmeapp.data.network

import com.example.contactmeapp.data.model.ContactResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("contacts")
    suspend fun getContacts(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10
    ): ContactResponse
}
