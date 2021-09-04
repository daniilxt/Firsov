package io.daniilxt.common.di

import android.content.Context
import retrofit2.Retrofit

interface CommonApi {

    fun context(): Context
    fun provideRetrofit(): Retrofit
}
