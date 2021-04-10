package com.harsewak.themoviedb.api

import okhttp3.*

/**
 * Created by Harsewak Singh on 10/04/21.
 */

class ApiInterceptor(private val apiKey: String) : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()
        val url = request.url().newBuilder()
            .addQueryParameter("api_key", apiKey)
            .build()
       //Amend headers if required
//        val headers = Headers.Builder()
//            .add("client-id", clientId)
//            .add("token", token)
//            .build()
        request = request.newBuilder().url(url).build()
            //.headers(headers).build()
        return chain.proceed(request)
    }
}