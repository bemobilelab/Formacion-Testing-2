package com.nramos.bemobilemockito.data.datasource

import com.nramos.bemobilemockito.data.service.DataService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class NetDatasource @Inject constructor(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val dataService: DataService
) {

    suspend fun getData() = withContext(dispatcher) {
        try {
            val response = dataService.getData()
            if(response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

}