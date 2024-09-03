package br.com.fiap.climaapp.fragments.home.location

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap.climaapp.data.CurrentLocation
import br.com.fiap.climaapp.data.CurrentWeather
import br.com.fiap.climaapp.data.Forecast
import br.com.fiap.climaapp.data.WeatherData
import br.com.fiap.climaapp.databinding.ItemContainerCurrentLocationBinding
import br.com.fiap.climaapp.databinding.ItemContainerCurrentWeatherBinding
import br.com.fiap.climaapp.databinding.ItemContainerForecastBinding
import coil.load

class WeatherDataAdapter(
    private val onLocationClicked: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private companion object {
        const val INDEX_CURRENT_LOCATION = 0
        const val INDEX_CURRENT_WEATHER = 1
        const val INDEX_FORECAST = 2
    }

    private val weatherData = mutableListOf<WeatherData>()

    fun updateData(newData: List<WeatherData>) {
        weatherData.clear()
        weatherData.addAll(newData)
        notifyDataSetChanged()
    }

    fun setCurrentLocation(currentLocation: CurrentLocation) {
        if (weatherData.isEmpty()) {
            weatherData.add(INDEX_CURRENT_LOCATION, currentLocation)
            notifyItemInserted(INDEX_CURRENT_LOCATION)
        } else {
            weatherData[INDEX_CURRENT_LOCATION] = currentLocation
            notifyItemChanged(INDEX_CURRENT_LOCATION)
        }
    }

    fun setCurrentWeather(currentWeather: CurrentWeather) {
        if (weatherData.getOrNull(INDEX_CURRENT_WEATHER) != null) {
            weatherData[INDEX_CURRENT_WEATHER] = currentWeather
            notifyItemChanged(INDEX_CURRENT_WEATHER)
        } else {
            weatherData.add(INDEX_CURRENT_WEATHER, currentWeather)
            notifyItemInserted(INDEX_CURRENT_WEATHER)
        }
    }

    fun setForecastData(forecast: List<Forecast>) {
        val startPosition = INDEX_FORECAST
        weatherData.removeAll { it is Forecast }
        notifyItemRangeRemoved(startPosition, weatherData.size - startPosition)
        weatherData.addAll(startPosition, forecast)
        notifyItemRangeInserted(startPosition, forecast.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            INDEX_CURRENT_LOCATION -> CurrentLocationViewHolder(
                ItemContainerCurrentLocationBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            INDEX_FORECAST -> ForecastViewHolder(
                ItemContainerForecastBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> CurrentWeatherViewHolder(
                ItemContainerCurrentWeatherBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CurrentLocationViewHolder -> holder.bind(weatherData[position] as CurrentLocation)
            is CurrentWeatherViewHolder -> holder.bind(weatherData[position] as CurrentWeather)
            is ForecastViewHolder -> holder.bind(weatherData[position] as Forecast)
        }
    }

    override fun getItemCount(): Int = weatherData.size

    override fun getItemViewType(position: Int): Int {
        return when (weatherData[position]) {
            is CurrentLocation -> INDEX_CURRENT_LOCATION
            is CurrentWeather -> INDEX_CURRENT_WEATHER
            is Forecast -> INDEX_FORECAST
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    inner class CurrentLocationViewHolder(
        private val binding: ItemContainerCurrentLocationBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(currentLocation: CurrentLocation) {
            with(binding) {
                textCurrentDate.text = currentLocation.date
                textCurrentLocation.text = currentLocation.location
                imageCurrentDate.setOnClickListener { onLocationClicked() }
                textCurrentLocation.setOnClickListener { onLocationClicked() }
            }
        }
    }

    inner class CurrentWeatherViewHolder(
        private val binding: ItemContainerCurrentWeatherBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(currentWeather: CurrentWeather) {
            with(binding) {
                imageIcon.load("https:${currentWeather.icon}") { crossfade(true) }
                textTemperature.text = String.format("%s\u00B0C", currentWeather.temperature)
                textWind.text = String.format("%s km/h", currentWeather.wind)
                textHumidity.text = String.format("%s%%", currentWeather.humidity)
                textChanceOfRain.text = String.format("%s%%", currentWeather.chanceOfRain)
            }
        }
    }

    inner class ForecastViewHolder(
        private val binding: ItemContainerForecastBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(forecast: Forecast) {
            with(binding) {
                textTime.text = forecast.time
                textTemperature.text = String.format("%s\u00B0C", forecast.temperature)
                textFeelsLikeTemperature.text = String.format("%s\u00B0C", forecast.feelsLikeTemperature)
                imageIcon.load("https:${forecast.icon}") { crossfade(true) }
            }
        }
    }
}
