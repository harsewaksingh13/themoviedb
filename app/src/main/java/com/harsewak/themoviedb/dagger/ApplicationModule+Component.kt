package com.harsewak.themoviedb

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.harsewak.themoviedb.api.ApiInterceptor
import com.harsewak.themoviedb.api.ServiceManager
import com.harsewak.themoviedb.api.ServiceManagerImpl
import com.harsewak.themoviedb.data.MovieInteraction
import com.harsewak.themoviedb.data.MovieInteractor
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * Created by Harsewak Singh on 10/04/21.
 */
@Module
class ApplicationModule(private val application: Application) {

    @Provides
    fun context(): Context = application.applicationContext

    @Provides
    fun serviceManager(retrofit: Retrofit): ServiceManager = ServiceManagerImpl(retrofit)

    @Provides
    @Singleton
    fun baseUrl(context: Context): String = context.getString(R.string.base_url)


    @Provides
    fun retrofit(
        baseUrl: String,
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit = Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient).addConverterFactory(
        gsonConverterFactory
    ).build()

    @Provides
    @Singleton
    fun gsonConverterFactory(gson: Gson): GsonConverterFactory = GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun gson(gsonBuilder: GsonBuilder): Gson = gsonBuilder.create()

    @Provides
    @Singleton
    @GsonNullSerialize
    fun gsonNullSerialize(gsonBuilder: GsonBuilder): Gson = gsonBuilder.serializeNulls().create()

    @Provides
    @Singleton
    fun gsonBuilder(): GsonBuilder = GsonBuilder()

    @Provides
    @Singleton
    fun okHttp(headerInterceptor: Interceptor, loggingInterceptor: HttpLoggingInterceptor): OkHttpClient = OkHttpClient.Builder().addInterceptor(
        loggingInterceptor
    ).addInterceptor(headerInterceptor).connectTimeout(15, TimeUnit.SECONDS).readTimeout(
        15,
        TimeUnit.SECONDS
    ).build()


    @Provides
    @Singleton
    fun loggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(
        HttpLoggingInterceptor.Level.BODY
    )

    @Provides
    @Singleton
    fun apiInterceptor(): Interceptor = ApiInterceptor("a3729833c3888d00507c755b735e6df9")

    @Provides
    fun movieInteractor(serviceManager: ServiceManager): MovieInteractor = MovieInteraction(
        serviceManager
    )
}

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    var movieInteractor: MovieInteractor

    var serviceManager: ServiceManager

}

@Qualifier
annotation class GsonNullSerialize