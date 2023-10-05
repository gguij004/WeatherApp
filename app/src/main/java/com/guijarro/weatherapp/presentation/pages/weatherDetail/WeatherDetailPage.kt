package com.guijarro.weatherapp.presentation.pages.weatherDetail

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.guijarro.weatherapp.R
import com.guijarro.weatherapp.domain.WeatherDomain
import com.guijarro.weatherapp.presentation.commonComponents.CustomAppBar
import com.guijarro.weatherapp.presentation.pages.weatherDetail.components.TextDefault
import com.guijarro.weatherapp.presentation.viewmodels.WeatherViewModel
import com.guijarro.weatherapp.utils.DbConstants.WEATHER_ICONS_BASE_URL
import com.guijarro.weatherapp.utils.UIState

@Composable
fun WeatherDetailPage(navHostController: NavHostController, vm: WeatherViewModel) {
    when (val state = vm.weather.collectAsState().value) {
        is UIState.ERROR -> {

        }

        is UIState.LOADING -> {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is UIState.SUCCESS -> {
            DetailsList(data = state.data, navHostController)
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsList(data: WeatherDomain, navHostController: NavHostController) {
    val tag: String = "WeatherPageDetails"
    val context = LocalContext.current
    val grade = kelvinToFahrenheit(data.main?.temp ?: 0.0)
    var color = Color.Black
    if (grade >= 77) {
        color = Color.Red
    } else if (grade <= 65) {
        color = Color.Blue
    }

    Scaffold(
        topBar = {
            CustomAppBar(
                title = "Weather Details",
                showBackButton = true,
                onBackClick = { navHostController.popBackStack() })
        },
        content = { padding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextDefault(
                    modifier = Modifier.padding(8.dp),
                    title = data.name ?: "Name not available",
                    textSize = 32,
                    fontStyle = FontWeight.ExtraBold,
                    textColor = Color.Black
                )
                TextDefault(
                    modifier = Modifier.padding(8.dp),
                    title = "${grade.toString().substringBefore(".")}º",
                    textSize = 50,
                    fontStyle = FontWeight.ExtraBold,
                    textColor = color
                )
                Row {
                    TextDefault(
                        modifier = Modifier.padding(8.dp),
                        title = data.weather?.first()?.main ?: "",
                        textSize = 22,
                        fontStyle = FontWeight.ExtraBold,
                        textColor = Color.Black
                    )
                    TextDefault(
                        modifier = Modifier.padding(8.dp),
                        title = "- ${data.weather?.first()?.description}",
                        textSize = 22,
                        fontStyle = FontWeight.ExtraBold,
                        textColor = Color.Black
                    )
                }
                Log.d(
                    tag,
                    "Icon Image: ${WEATHER_ICONS_BASE_URL}${data.weather?.first()?.icon}@4x.png"
                )
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data("${WEATHER_ICONS_BASE_URL}${data.weather?.first()?.icon}@2x.png")
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.ic_launcher_foreground),
                    error = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "ImageIcon",
                    contentScale = ContentScale.Crop, modifier = Modifier.size(150.dp)
                )
            }
        }
    )
}

fun kelvinToFahrenheit(kelvin: Double): Double = (kelvin - 273.15) * 9 / 5 + 32
