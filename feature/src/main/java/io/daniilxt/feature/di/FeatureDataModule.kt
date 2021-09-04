package io.daniilxt.feature.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import io.daniilxt.common.di.scope.FeatureScope
import io.daniilxt.feature.data.remote.api.FeatureApiService
import io.daniilxt.feature.data.remote.source.FeatureDataSourceImpl
import io.daniilxt.feature.data.repository.FeatureRepositoryImpl
import io.daniilxt.feature.data.source.FeatureDataSource
import io.daniilxt.feature.domain.repository.FeatureRepository
import retrofit2.Retrofit

@Module
abstract class FeatureDataModule {
    @Binds
    @FeatureScope
    abstract fun bindFeatureRepository(featureRepositoryImpl: FeatureRepositoryImpl): FeatureRepository

    @Binds
    @FeatureScope
    abstract fun bindFeatureDataSource(featureDataSourceImpl: FeatureDataSourceImpl): FeatureDataSource

    companion object {
        @Provides
        @FeatureScope
        fun provideFeatureApiService(retrofit: Retrofit): FeatureApiService =
            retrofit.create(FeatureApiService::class.java)
    }
}