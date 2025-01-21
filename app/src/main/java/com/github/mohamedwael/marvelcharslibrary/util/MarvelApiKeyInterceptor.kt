package com.github.mohamedwael.marvelcharslibrary.util

import okhttp3.Interceptor
import okhttp3.Response
import java.math.BigInteger
import java.security.MessageDigest

private const val API_KEY = "apikey"
private const val HASH_KEY = "hash"
private const val TIME_KEY = "ts"

class MarvelApiKeyInterceptor(private val apiKey: String, val privateKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        // Add the API key to the query parameters
        val ts = System.currentTimeMillis().toString()
        val newUrl = originalUrl.newBuilder()
            .addQueryParameter(TIME_KEY, ts)
            .addQueryParameter(HASH_KEY, generateHash(ts, privateKey = privateKey, publicKey = apiKey))
            .addQueryParameter(API_KEY, apiKey)
            .build()

        // Create a new request with the updated URL
        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }

    fun generateHash(ts: String, privateKey: String, publicKey: String): String {
        val input = "$ts$privateKey$publicKey"
        val md5 = MessageDigest.getInstance("MD5")
        return BigInteger(1, md5.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }
}