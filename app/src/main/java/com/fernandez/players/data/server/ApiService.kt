package com.fernandez.players.data.server

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Call
import java.io.IOException


interface ApiService {


    companion object Factory {

        const val USERS_ENDPOINT = "bins/"

        const val GET_PLAYERS = "66851"

    }

    @retrofit2.http.GET(USERS_ENDPOINT+GET_PLAYERS) fun getPlayers(): Call<String>


}


//Header interceptor for debug purposes

class HeaderInterceptor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder: Request.Builder


        requestBuilder = request.newBuilder()
            .addHeader("Content-Type", "application/json")

        Log.i("REQUEST",
            String.format("Sending request %s on %s %s", request.url(), chain.connection(), request.headers()))

        val response = chain.proceed(requestBuilder.build())

        Log.i("REQUEST",
            String.format("Received response for %s, headers: %s", request.url(), response.body()))

        val body = ResponseBody.create(response.body()?.contentType(), response.body()!!.string())
        return response.newBuilder().body(body).build()
    }
}

