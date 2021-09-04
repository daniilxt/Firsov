package io.daniilxt.feature.domain.usecase

import io.daniilxt.common.error.RequestResult
import io.daniilxt.feature.domain.model.GifModel
import io.daniilxt.feature.domain.repository.FeatureRepository
import io.reactivex.Single
import javax.inject.Inject

class GetHotGifListUseCase @Inject constructor(private val featureRepository: FeatureRepository) {
    operator fun invoke(page: Int): Single<RequestResult<List<GifModel>>> {
        return featureRepository.getHotGifList(page)
    }
}