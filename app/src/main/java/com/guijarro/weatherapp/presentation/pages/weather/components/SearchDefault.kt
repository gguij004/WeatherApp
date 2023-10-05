package com.guijarro.weatherapp.presentation.pages.weather.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.guijarro.weatherapp.presentation.viewmodels.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchDefault(vm: WeatherViewModel) {
    val state = vm.city.collectAsState()

    OutlinedTextField(
        value = state.value,
        onValueChange = { vm.setCity(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        label = { Text(text = "Type the city here...") })
}