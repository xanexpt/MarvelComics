package com.badjoras.marvel.dependencyinjection.modules

import com.badjoras.marvel.BuildConfig
import com.badjoras.marvel.api.AuthenticationInterceptor
import com.badjoras.marvel.api.MarvelApi
import com.badjoras.marvel.api.MarvelApi.Companion.BASE_URL
import com.badjoras.marvel.dependencyinjection.scope.ApplicationScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {

    @ApplicationScope
    @Provides
    fun provideClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if(BuildConfig.DEBUG){
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)
        }
        builder.addInterceptor(AuthenticationInterceptor())
        builder.readTimeout(60, TimeUnit.SECONDS)
        builder.connectTimeout(60, TimeUnit.SECONDS)
        return builder.build()
    }

    @ApplicationScope
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    @ApplicationScope
    @Provides
    fun provideOlxAPI(retrofit: Retrofit): MarvelApi {
        return retrofit.create(MarvelApi::class.java)
    }
}