package io.daniilxt.fintech.di.app

import dagger.BindsInstance
import dagger.Component
import io.daniilxt.common.di.CommonApi
import io.daniilxt.common.di.modules.CommonModule
import io.daniilxt.common.di.modules.NetworkModule
import io.daniilxt.common.di.scope.ApplicationScope
import io.daniilxt.fintech.App
import io.daniilxt.fintech.di.deps.ComponentHolderModule

@ApplicationScope
@Component(
    modules = [
        AppModule::class,
        CommonModule::class,
        NetworkModule::class,
        ComponentHolderModule::class,
        FeatureManagerModule::class,
        NavigationModule::class
    ]
)
interface AppComponent : CommonApi {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: App): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}