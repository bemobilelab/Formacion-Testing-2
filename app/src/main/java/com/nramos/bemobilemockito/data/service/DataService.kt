package com.nramos.bemobilemockito.data.service

import com.nramos.bemobilemockito.data.PEOPLE_ROUTE
import com.nramos.bemobilemockito.data.model.ResponseDTO
import retrofit2.Response
import retrofit2.http.GET

interface DataService {

    @GET(PEOPLE_ROUTE)
    suspend fun getData(): Response<ResponseDTO>

}