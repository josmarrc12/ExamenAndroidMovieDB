package com.osmar.examen.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.osmar.web.APIConstants
import com.osmar.web.ApiService
import com.osmar.web.serializers.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofit(): ApiService{
        return Retrofit.Builder()
            .baseUrl(APIConstants.BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson()))
            .client(provideHttpClient())
            .build()
            .create(ApiService::class.java)
    }


    private fun loggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor

    }


    private fun provideHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor())
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer " + APIConstants.TOKEN)
                    .build()
                chain.proceed(newRequest)
            }
            .build()
    }


    private fun gson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(Boolean::class.java, BooleanSerializer())
            .registerTypeAdapter(Boolean::class.java, BooleanDeserializer())
            .registerTypeAdapter(Boolean::class.javaPrimitiveType, BooleanSerializer())
            .registerTypeAdapter(Boolean::class.javaPrimitiveType, BooleanDeserializer())
            .registerTypeAdapter(Date::class.java, DateSerializer())
            .registerTypeAdapter(Date::class.java, DateDeserializaer())
            .create()
    }
}