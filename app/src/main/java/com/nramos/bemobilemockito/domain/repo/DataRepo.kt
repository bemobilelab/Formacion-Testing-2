package com.nramos.bemobilemockito.domain.repo

import com.nramos.bemobilemockito.domain.Either
import com.nramos.bemobilemockito.domain.model.Character

interface DataRepo {

    suspend fun getData(): Either<List<Character>, String>

}