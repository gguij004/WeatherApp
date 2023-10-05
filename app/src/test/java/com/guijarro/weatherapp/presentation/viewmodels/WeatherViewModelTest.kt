package com.guijarro.weatherapp.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.guijarro.weatherapp.domain.WeatherDomain
import com.guijarro.weatherapp.domain.usecases.GetCityWeatherUseCase
import com.guijarro.weatherapp.domain.usecases.GetLocationWeatherUseCase
import com.guijarro.weatherapp.utils.UIState
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class WeatherViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private lateinit var viewModel: WeatherViewModel
    private var cityUseCases: GetCityWeatherUseCase = mockk(relaxed = true)
    private var locationUseCases: GetLocationWeatherUseCase = mockk(relaxed = true)

    @Before
    fun setUp() {
        cityUseCases = mockk()
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `setCity should update the city value`() {
        // Arrange
        val cityName = "London"

        // Act
        viewModel.setCity(cityName)

        // Assert
        assertEquals(cityName, viewModel.city.value)
    }

    @Test
    fun `getWeatherCity should update the weather value`() {
        val job = testScope.launch {
            // Arrange
            val cityName = "London"
            val domainWeather = WeatherDomain(/* weather details */)
            val expectedUIState = UIState.SUCCESS(domainWeather)
            val weatherFlow = MutableStateFlow(expectedUIState)

            coEvery { cityUseCases.invoke(cityName) } returns weatherFlow

            // Act
            viewModel.getWeatherCity()

            // Assert
            assertEquals(expectedUIState, viewModel.weather.first())
        }
        job.cancel()
    }
}