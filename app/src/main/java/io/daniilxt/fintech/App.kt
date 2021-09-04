package io.daniilxt.fintech

import android.app.Application
import io.daniilxt.common.di.CommonApi
import io.daniilxt.common.di.FeatureContainer
import io.daniilxt.fintech.di.app.AppComponent
import io.daniilxt.fintech.di.app.DaggerAppComponent
import io.daniilxt.fintech.di.deps.FeatureHolderManager
import io.daniilxt.fintech.log.ReleaseTree
import timber.log.Timber
import javax.inject.Inject

open class App: Application(), FeatureContainer {

    @Inject
    lateinit var featureHolderManager: FeatureHolderManager

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
                .builder()
                .application(this)
                .build()

        appComponent.inject(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }

    }

    override fun <T> getFeature(key: Class<*>): T {
        return featureHolderManager.getFeature<T>(key)!!
    }

    override fun releaseFeature(key: Class<*>) {
        featureHolderManager.releaseFeature(key)
    }

    override fun commonApi(): CommonApi {
        return appComponent
    }
}