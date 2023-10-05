package com.guijarro.weatherapp.presentation.pages.weather.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ButtonDefault(title: String, onTap: () -> Unit) {

    Button(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth(),
        onClick = {
            onTap()
        }
    ) {
        Text(text = title, textAlign = TextAlign.Center)
    }
}