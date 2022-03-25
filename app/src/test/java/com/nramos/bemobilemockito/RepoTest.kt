package com.nramos.bemobilemockito

import com.google.gson.Gson
import com.nramos.bemobilemockito.data.datasource.NetDatasource
import com.nramos.bemobilemockito.data.model.CharacterDTO
import com.nramos.bemobilemockito.data.model.ResponseDTO
import com.nramos.bemobilemockito.data.model.toDomain
import com.nramos.bemobilemockito.data.repo.DataRepoImpl
import com.nramos.bemobilemockito.domain.Either
import com.nramos.bemobilemockito.domain.onSuccess
import com.nramos.bemobilemockito.domain.repo.DataRepo
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RepoTest {

    @Mock
    lateinit var mockReponseDTO: ResponseDTO

    @Mock
    lateinit var datasource: NetDatasource

    lateinit var repo: DataRepo

    lateinit var mockCharactersDTO: List<CharacterDTO>

    @Before
    fun setup() {
        mockCharactersDTO = Gson().fromJson(charactersJson, Array<CharacterDTO>::class.java).toList()
        repo = DataRepoImpl(netDatasource = datasource)
    }

    @Test
    fun `get data and success`() {
        runBlocking {
            Mockito.`when`(mockReponseDTO.results).thenReturn(mockCharactersDTO)
            Mockito.`when`(datasource.getData()).thenReturn(mockReponseDTO)
            assert(repo.getData() is Either.Success)
            repo.getData().onSuccess {
                it.forEachIndexed { index, character ->
                    assert(character == mockCharactersDTO[index].toDomain())
                }
            }
        }
    }

    @Test
    fun `get data and fail`() {
        runBlocking {
            Mockito.`when`(datasource.getData()).thenReturn(null)
            assert(repo.getData() is Either.Failure)
        }
    }

}