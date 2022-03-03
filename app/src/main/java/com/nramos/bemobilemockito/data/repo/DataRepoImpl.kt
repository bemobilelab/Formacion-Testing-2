package com.nramos.bemobilemockito.data.repo

import com.nramos.bemobilemockito.data.datasource.NetDatasource
import com.nramos.bemobilemockito.data.model.toDomain
import com.nramos.bemobilemockito.domain.Either
import com.nramos.bemobilemockito.domain.eitherFailure
import com.nramos.bemobilemockito.domain.eitherSuccess
import com.nramos.bemobilemockito.domain.model.Character
import com.nramos.bemobilemockito.domain.repo.DataRepo
import javax.inject.Inject

class DataRepoImpl @Inject constructor(
    private val netDatasource: NetDatasource
) : DataRepo {

    override suspend fun getData(): Either<List<Character>, String> {
        val result = netDatasource.getData()
        result?.let {
            return eitherSuccess(it.toDomain())
        }
        return eitherFailure("Null error")
    }

}