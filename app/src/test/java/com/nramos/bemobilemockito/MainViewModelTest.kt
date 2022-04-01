package com.nramos.bemobilemockito

import com.google.gson.Gson
import com.nramos.bemobilemockito.domain.eitherFailure
import com.nramos.bemobilemockito.domain.eitherSuccess
import com.nramos.bemobilemockito.domain.model.Character
import com.nramos.bemobilemockito.domain.repo.DataRepo
import com.nramos.bemobilemockito.domain.usecases.GetDataUseCase
import com.nramos.bemobilemockito.presentation.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @Mock
    lateinit var getDataUseCase: GetDataUseCase

    lateinit var viewModel: MainViewModel

    lateinit var mockCharacters: List<Character>

    @ExperimentalCoroutinesApi
    val dispatcher = UnconfinedTestDispatcher()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        mockCharacters = Gson().fromJson(charactersJson, Array<Character>::class.java).toList()
        viewModel = MainViewModel(getDataUseCase = getDataUseCase)
    }

    @Test
    fun `init viewModel test`() {
        runBlocking {
            assert(viewModel.state.value.loading)
            assert(viewModel.state.value.error.not())
            assert(viewModel.state.value.characters.isEmpty())
        }
    }

    @Test
    fun `get data and success`()  {
        runBlocking {
            Mockito.`when`(getDataUseCase()).thenReturn(eitherSuccess(mockCharacters))
            viewModel.getData()
            assert(viewModel.state.value.loading.not())
            assert(viewModel.state.value.error.not())
            assert(viewModel.state.value.characters.isNotEmpty())
        }
    }

    @Test
    fun `get data and fail`()  {
        runBlocking {
            Mockito.`when`(getDataUseCase()).thenReturn(eitherFailure("Error"))
            viewModel.getData()
            assert(viewModel.state.value.loading.not())
            assert(viewModel.state.value.error)
            assert(viewModel.state.value.characters.isEmpty())
        }
    }

}