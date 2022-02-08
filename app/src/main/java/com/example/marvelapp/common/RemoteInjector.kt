package com.example.marvelapp.common

import android.util.Log
import com.example.marvelapp.BuildConfig
import com.example.marvelapp.retrofit.RetrofitAPI
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigInteger
import java.security.MessageDigest
import java.util.concurrent.TimeUnit

/**
 * @see[RemoteInjector] acts as a object provider and creator
 */
object RemoteInjector {

    private const val TIMEOUT = 30L
    private const val baseurl =  "https://gateway.marvel.com:443/v1/public/"


    private val authInterceptor = { chain: Interceptor.Chain ->
        val ts = System.currentTimeMillis()

        val hash =
            "$ts${BuildConfig.MARVEL_PRIVATE_API_KEY}${BuildConfig.MARVEL_PUBLIC_API_KEY}".encrypt()

        val request = chain.request()

        val url = request.url
            .newBuilder()
            .addQueryParameter("ts", ts.toString())
            .addQueryParameter("apikey", BuildConfig.MARVEL_PUBLIC_API_KEY)
            .addQueryParameter("hash", hash)
            .build()
        Log.e("TAG", "request=$url")

        val updated = request.newBuilder()
            .url(url)
            .build()

        chain.proceed(updated)
    }


    private val okHttpClientBuilder = OkHttpClient.Builder()
        .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(authInterceptor)
        .addInterceptor(getHttpLogger())
        .build()

    fun injectDoggoApiService(retrofit: Retrofit = getRetrofit()): RetrofitAPI {
        return retrofit.create(RetrofitAPI::class.java)
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClientBuilder)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }



    private fun getHttpLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }



    private fun String.encrypt(): String =
        BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
            .toString(16).padStart(32, '0')

}