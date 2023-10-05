import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.guijarro.weatherapp.presentation.commonComponents.CustomAppBar
import com.guijarro.weatherapp.presentation.pages.weather.components.ButtonDefault
import com.guijarro.weatherapp.presentation.pages.weather.components.SearchDefault
import com.guijarro.weatherapp.presentation.viewmodels.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MissingPermission")
@Composable
fun WeatherPage(navHostController: NavHostController, vm: WeatherViewModel) {
    val tag: String = "WeatherPage"
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CustomAppBar(title = "Weather")
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding), horizontalAlignment = Alignment.CenterHorizontally
            ) {

                SearchDefault(vm = vm)
                Spacer(modifier = Modifier.size(8.dp))
                ButtonDefault(
                    title = "Search weather"
                ) {
                    Log.d(tag, "SearchWeatherButtonTapped")
                    vm.onSearchWeatherTap(navHostController)
                }

                ButtonDefault(
                    title = "Weather in my location"
                ) {
                    Log.d(tag, "SearchWeatherByLocationButtonTapped")
                    vm.onSearchWeatherByLocationTap(navHostController, context as Activity)
                }
            }
        }
    )
}