package com.fernandez.players.domain.di

import com.fernandez.players.data.server.ApiService
import com.fernandez.players.data.server.HeaderInterceptor
import com.fernandez.players.data.server.NetworkHandler
import com.fernandez.players.data.server.ServerResponseMapper
import com.fernandez.players.domain.repository.PlayerRepository
import com.fernandez.players.domain.uc.players.GetPlayers
import com.fernandez.players.presentation.main.MainActivityViewModel
import com.fernandez.players.utils.Constants
import okhttp3.OkHttpClient
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

val retrofitModule = module {


    single {
        OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(HeaderInterceptor())
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS).build()
    }

    //API Service
    single {
        Retrofit.Builder()
            .baseUrl(Constants.END_POINT_URL)
            .client(get())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build().create(ApiService::class.java)
    }

}

val mainModule = module{

    single{ NetworkHandler(get()) }
}

val playerModule = module {

    // single instance of PlayerRespository
    single<PlayerRepository> { PlayerRepository.Network(get(),get(),ServerResponseMapper) }

    //player use case

    factory {
        GetPlayers(get())
    }

    // UsersViewModel ViewModel
    viewModel { MainActivityViewModel(get()) }

}