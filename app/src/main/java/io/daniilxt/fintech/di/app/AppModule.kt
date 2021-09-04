package io.daniilxt.fintech.di.app

import android.content.Context
import dagger.Module
import dagger.Provides
import io.daniilxt.common.di.scope.ApplicationScope
import io.daniilxt.fintech.App

@Module
class AppModule {

    @Provides
    @ApplicationScope
    fun provideContext(application: App): Context {
        return application
    }
}