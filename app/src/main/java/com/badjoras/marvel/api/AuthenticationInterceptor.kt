package com.badjoras.marvel.api

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.math.BigInteger
import java.security.MessageDigest

class AuthenticationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val timeStamp: String = System.currentTimeMillis().toString()
        val publicKey: String = "0dd146f8b09f070321a176f212b235a0"
        val privateKey: String = "f914898e328702b4f63e22377e5f1371bdc05aaa"

        var request: Request = chain.request()

        val url: HttpUrl? = request
            .url()
            .newBuilder()
            .addQueryParameter(QUERY_PARAMETER_TIME_STAMP, timeStamp)
            .addQueryParameter(QUERY_PARAMETER_API_KEY, publicKey)
            .addQueryParameter(QUERY_PARAMETER_HASH, getHashMd5(timeStamp, publicKey, privateKey))
            .build()

        request = request.newBuilder().url(url!!).build()

        return chain.proceed(request)
    }

    /**
     * https://developer.marvel.com/documentation/authorization
     *  md5(ts+privateKey+publicKey)
     */
    private fun getHashMd5(timeStamp: String, publicKey: String, privateKey: String): String {
        val stringToHash = timeStamp + privateKey + publicKey
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(stringToHash.toByteArray())).toString(16).padStart(32, '0')
    }

    companion object {
        const val QUERY_PARAMETER_TIME_STAMP = "ts"
        const val QUERY_PARAMETER_API_KEY = "apikey"
        const val QUERY_PARAMETER_HASH = "hash"
    }
}