package com.pemwa.topnews.util

import com.pemwa.topnews.BuildConfig

/**
 * The base URL from which we will be fetching the gdg data.
 */
const val BASE_URL = "https://newsapi.org/v2/"

/**
 * Authentication API Key to be attached to a network request.
 *
 * The actual key is defined in the BuildConfig
 * to avoid exposing it to the public for security reasons.
 */
const val apiKey = BuildConfig.ApiKey
