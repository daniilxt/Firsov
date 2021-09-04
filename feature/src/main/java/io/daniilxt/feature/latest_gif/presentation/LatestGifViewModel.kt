package io.daniilxt.feature.latest_gif.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.daniilxt.common.error.RequestResult
import io.daniilxt.feature.domain.model.GifModel
import io.daniilxt.feature.domain.usecase.GetLatestGifListUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class LatestGifViewModel(
    private val getLatestGifListUseCase: GetLatestGifListUseCase
) :
    ViewModel() {
    private val disposable = CompositeDisposable()

    private val _latestGifList: MutableStateFlow<List<GifModel>> =
        MutableStateFlow(emptyList())
    val latestGifList: StateFlow<List<GifModel>> get() = _latestGifList

    init {
        viewModelScope.launch {
            loadLatestGifList(1)
        }
    }

    fun loadLatestGifList(page: Int) {
        getLatestGifListUseCase.invoke(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                when (it) {
                    is RequestResult.Success -> {
                        Timber.tag(TAG).i("SUCCESS")
                        it.data.forEach { gifData ->
                            Timber.i("DATA $gifData")
                        }
                    }
                    is RequestResult.Error -> {
                        Timber.tag(TAG).i("ERROR")
                    }
                }
            }, {
                Timber.tag(TAG).e(it)
            })
            .addTo(disposable)
    }

    companion object {
        private val TAG = LatestGifViewModel::class.java.simpleName
    }
}