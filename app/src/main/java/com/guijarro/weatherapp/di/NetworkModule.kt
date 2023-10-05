package com.guijarro.weatherapp.di

import com.google.gson.Gson
import com.guijarro.weatherapp.data.rest.ServiceApi
import com.guijarro.weatherapp.data.rest.ServiceApi.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    /**
     * Provides an instance of Retrofit with the base URL and GsonConverterFactory.
     * @param okHttpClient The OkHttpClient instance to use.
     * @param gson The Gson instance to use for JSON conversion.
     */
    @Provides
    fun providesRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

    /**
     * Provides an instance of OkHttpClient with the HttpLoggingInterceptor and timeouts set.
     * @param httpLoggingInterceptor The HttpLoggingInterceptor instance to use for logging.
     * Also defining the timeouts for new connections
     */
    @Provides
    fun providesOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    /**
     * Provides an instance of HttpLoggingInterceptor with the log level set to BODY.
     */
    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    /**
     * Provides an instance of Gson.
     */
    @Provides
    fun providesGson(): Gson = Gson()

    /**
     * Provides an instance of ServiceApi class.
     * @param retrofit The Retrofit instance to use.
     */
    @Provides
    fun providesServiceApi(retrofit: Retrofit): ServiceApi =
        retrofit.create(ServiceApi::class.java)

    /**
     * Provides an instance of CoroutineDispatcher for I/O operations.
     */
    @Provides
    fun providesIODispatcher(): CoroutineDispatcher =
        Dispatchers.IO

}