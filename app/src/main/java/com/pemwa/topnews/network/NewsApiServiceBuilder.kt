package com.pemwa.topnews.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.pemwa.topnews.util.BASE_URL
import com.pemwa.topnews.util.apiKey
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Building the Moshi object that Retrofit will be using.
 * We are adding the Kotlin adapter for full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Creating an [apiKeyInterceptor] for the network request
 */
private val apiKeyInterceptor: Interceptor
    get() = Interceptor { chain ->
        val original = chain.request()
        val request = original.url()

        val url = request.newBuilder()
            .addQueryParameter("apiKey", apiKey)
            .build()

        val requestBuilder = original.newBuilder().url(url)
        val requested = requestBuilder.build()

        chain.proceed(requested)

    }

/**
 * Creating a [httpLoggingInterceptor] for the network request
 */
private val httpLoggingInterceptor: Interceptor
    get() {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

/**
 * This is main entry point for network access. Call like `Network.news.getTopHeadlines()`
 */
object Network {
    // Configuring OkHttpClient and adding the interceptors
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(apiKeyInterceptor)
        .addInterceptor(httpLoggingInterceptor)
        .build()

    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val news = retrofit.create(NewsApiService::class.java)

}
