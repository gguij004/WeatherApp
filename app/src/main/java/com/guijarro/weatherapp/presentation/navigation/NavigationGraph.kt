package com.guijarro.weatherapp.presentation.navigation

import WeatherPage
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.guijarro.weatherapp.presentation.pages.weatherDetail.WeatherDetailPage
import com.guijarro.weatherapp.presentation.viewmodels.WeatherViewModel
@Composable
fun NavigationGraph(navHostController: NavHostController, vm: WeatherViewModel) {

    NavHost(navController = navHostController, startDestination = Routes.WeatherScreen.route ){

        composable(Routes.WeatherScreen.route){
            WeatherPage(navHostController, vm)
        }

        composable(Routes.WeatherDetails.route){
            WeatherDetailPage(navHostController, vm = vm)
        }
    }

}