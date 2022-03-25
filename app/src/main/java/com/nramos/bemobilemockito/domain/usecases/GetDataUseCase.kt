package com.nramos.bemobilemockito.domain.usecases

import com.nramos.bemobilemockito.domain.Either
import com.nramos.bemobilemockito.domain.eitherFailure
import com.nramos.bemobilemockito.domain.eitherSuccess
import com.nramos.bemobilemockito.domain.model.Character
import com.nramos.bemobilemockito.domain.onFailure
import com.nramos.bemobilemockito.domain.onSuccess
import com.nramos.bemobilemockito.domain.repo.DataRepo
import javax.inject.Inject

open class GetDataUseCase @Inject constructor(
    private val repo: DataRepo
) {

    suspend operator fun invoke() : Either<List<Character>, String> {
        val result = repo.getData()
        result.onSuccess {
            if(it.isEmpty()) return eitherFailure("Empty error")
            return eitherSuccess(it.sortedBy{ it.name }.take(5))
        }.onFailure {
            return eitherFailure("Error")
        }
        return eitherFailure("Undefined Error")
    }

}