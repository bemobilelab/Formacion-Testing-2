package com.nramos.bemobilemockito

import com.nramos.bemobilemockito.data.datasource.NetDatasource
import com.nramos.bemobilemockito.data.model.ResponseDTO
import com.nramos.bemobilemockito.data.service.DataService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import java.lang.Exception

@RunWith(MockitoJUnitRunner::class)
class DatasourceTest {

    @Mock
    lateinit var service: DataService

    @Mock
    lateinit var mockResponse: Response<ResponseDTO>

    @Mock
    lateinit var mockReponseDTO: ResponseDTO

    lateinit var datasource: NetDatasource

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        datasource = NetDatasource(
            dispatcher = UnconfinedTestDispatcher(),
            dataService = service
        )
    }

    @Test
    fun `get data and success`() {
        runBlocking {
            Mockito.`when`(mockResponse.isSuccessful).thenReturn(true)
            Mockito.`when`(mockResponse.body()).thenReturn(mockReponseDTO)
            Mockito.`when`(service.getData()).thenReturn(mockResponse)
            assert(datasource.getData() != null)
            assert(datasource.getData() == mockReponseDTO)
        }
    }

    @Test
    fun `get data and fail`() {
        runBlocking {
            Mockito.`when`(mockResponse.isSuccessful).thenReturn(false)
            Mockito.`when`(service.getData()).thenReturn(mockResponse)
            assert(datasource.getData() == null)
        }
    }

    @Test
    fun `get data and exception`() {
        runBlocking {
            given(service.getData()).willAnswer {
                throw Exception()
            }
            assert(datasource.getData() == null)
        }
    }

}