package io.daniilxt.feature.latest_gif.presentation

import androidx.lifecycle.ViewModel
import io.daniilxt.common.error.RequestResult
import io.daniilxt.feature.domain.model.GifModel
import io.daniilxt.feature.domain.usecase.GetLatestGifListUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber

class LatestGifViewModel(
    private val getLatestGifListUseCase: GetLatestGifListUseCase
) :
    ViewModel() {
    private val disposable = CompositeDisposable()

    private val _latestGifList: MutableStateFlow<List<GifModel>> =
        MutableStateFlow(emptyList())
    val latestGifList: StateFlow<List<GifModel>> get() = _latestGifList

    private val _currentGif: MutableStateFlow<GifModel?> = MutableStateFlow(null)
    val currentGif: StateFlow<GifModel?> get() = _currentGif

    private var position: Int = 0
    private var page: Int = 0

    fun loadLatestGifList(page: Int = this.page) {
        getLatestGifListUseCase.invoke(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                when (it) {
                    is RequestResult.Success -> {
                        Timber.tag(TAG).i("SUCCESS")
                        _latestGifList.value = it.data
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

    fun nextGif() {
        if (_latestGifList.value.size > position + 1) {
            position++
            _currentGif.value = _latestGifList.value[position]
        } else {
            loadLatestGifList(++page)
            position = 0
        }
        Timber.i("PAGE $page  position $position")
    }

    fun prevGif() {
        if (position in 1.._latestGifList.value.size) {
            position--
            _currentGif.value = _latestGifList.value[position]
        } else if (page != 0) {
            loadLatestGifList(--page)
            position = _latestGifList.value.size - 1
        }
        Timber.i("PAGE $page  position $position")
    }

    fun setGifFromCurrentPosition() {
        if (_latestGifList.value.isNotEmpty()) {
            _currentGif.value = _latestGifList.value[position]
        }
    }

    companion object {
        private val TAG = LatestGifViewModel::class.java.simpleName
    }
}