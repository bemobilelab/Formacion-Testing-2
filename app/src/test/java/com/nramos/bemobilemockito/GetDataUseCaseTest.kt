package com.nramos.bemobilemockito

import com.google.gson.Gson
import com.nramos.bemobilemockito.domain.Either
import com.nramos.bemobilemockito.domain.eitherFailure
import com.nramos.bemobilemockito.domain.eitherSuccess
import com.nramos.bemobilemockito.domain.model.Character
import com.nramos.bemobilemockito.domain.onFailure
import com.nramos.bemobilemockito.domain.onSuccess
import com.nramos.bemobilemockito.domain.repo.DataRepo
import com.nramos.bemobilemockito.domain.usecases.GetDataUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetDataUseCaseTest {

    @Mock
    lateinit var mockDataRepo: DataRepo

    lateinit var useCaseToTest: GetDataUseCase

    lateinit var mockCharacters: List<Character>

    @Before
    fun setup() {
        mockCharacters = Gson().fromJson(charactersJson, Array<Character>::class.java).toList()
        useCaseToTest = GetDataUseCase(mockDataRepo)
    }

    @Test
    fun `get data and success`()  {
        runBlocking {
            Mockito.`when`(mockDataRepo.getData()).thenReturn(eitherSuccess(mockCharacters))
            assert(useCaseToTest() is Either.Success)
            useCaseToTest().onSuccess {
                assert(it.isNotEmpty())
                assert(it.size <= 5)
                assert(it.first().name == "Beru Whitesun lars")
            }
        }
    }

    @Test
    fun `get data but empty`()  {
        runBlocking {
            Mockito.`when`(mockDataRepo.getData()).thenReturn(eitherSuccess(emptyList()))
            assert(useCaseToTest() is Either.Failure)
            useCaseToTest().onFailure {
                assert(it == "Empty error")
            }
        }
    }

    @Test
    fun `get data and fail`()  {
        runBlocking {
            Mockito.`when`(mockDataRepo.getData()).thenReturn(eitherFailure(""))
            assert(useCaseToTest() is Either.Failure)
            useCaseToTest().onFailure {
                assert(it == "Error")
            }
        }
    }

    @Test
    fun `get data and undefined failure`()  {
        runBlocking {
            Mockito.`when`(mockDataRepo.getData()).thenReturn(null)
            assert(useCaseToTest() is Either.Failure)
            useCaseToTest().onFailure {
                assert(it == "Undefined Error")
            }
        }
    }

}