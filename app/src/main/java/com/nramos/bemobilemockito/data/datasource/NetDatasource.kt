package com.nramos.bemobilemockito.data.datasource

import com.nramos.bemobilemockito.data.service.DataService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NetDatasource @Inject constructor(
   private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
   private val dataService: DataService
) {

    suspend fun getData() = withContext(ioDispatcher) {
        val response = dataService.getData()
        if(response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

}