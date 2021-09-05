package io.daniilxt.feature.latest_gif.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.daniilxt.common.error.RequestResult
import io.daniilxt.feature.database.GifsDB
import io.daniilxt.feature.database.models.GifDataBaseModel
import io.daniilxt.feature.database.models.toGifModel
import io.daniilxt.feature.database.repository.GifRepository
import io.daniilxt.feature.domain.model.GifModel
import io.daniilxt.feature.domain.model.GifTopic
import io.daniilxt.feature.domain.model.toGifDataBaseModel
import io.daniilxt.feature.domain.usecase.GetLatestGifListUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
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

    private val _currentGif: MutableStateFlow<GifModel?> = MutableStateFlow(null)
    val currentGif: StateFlow<GifModel?> get() = _currentGif

    private val _layoutState: MutableStateFlow<LayoutState> =
        MutableStateFlow(LayoutState.ShowGifViewer)
    val layoutState: StateFlow<LayoutState> get() = _layoutState

    private val _backButtonState: MutableStateFlow<BackButtonState> =
        MutableStateFlow(BackButtonState.Disabled)
    val backButtonState: StateFlow<BackButtonState> get() = _backButtonState

    private var position: Int = 0
    private var page: Int = 0

    //TODO make normal
    private var repository: GifRepository? = null

    fun initDatabase(context: Context) {
        val userDao = GifsDB.getDatabase(context).gifsDao()
        repository = GifRepository(userDao)
    }

    private fun addGifListToDatabase(data: List<GifDataBaseModel>) =
        viewModelScope.launch(Dispatchers.IO) {
            repository?.addGifList(data)
        }

    private fun getGifListFromDatabase(
        page: Int,
        gifTopic: GifTopic,
        callback: (gifList: List<GifDataBaseModel>?) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val gifListFromDB = repository?.getGifListFromPage(page, gifTopic)
            callback(gifListFromDB)
        }
    }

    fun getGifList(page: Int = this.page) {
        getGifListFromDatabase(page, GifTopic.LATEST) {
            Timber.i("DATAAAA $it")
            if (!it.isNullOrEmpty()) {
                _latestGifList.value = it.map { gifDataBaseModel -> gifDataBaseModel.toGifModel() }
            } else {
                loadLatestGifList()
            }
        }
    }

    fun loadLatestGifList(page: Int = this.page) {
        getLatestGifListUseCase.invoke(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                when (it) {
                    is RequestResult.Success -> {
                        Timber.tag(TAG).i("SUCCESS")
                        _latestGifList.value = it.data
                        val data = it.data.map { gifModel ->
                            gifModel.toGifDataBaseModel(page, GifTopic.LATEST)
                        }
                        addGifListToDatabase(data)
                        _layoutState.value = LayoutState.ShowGifViewer
                    }
                    is RequestResult.Error -> {
                        Timber.tag(TAG).i("ERROR")
                    }
                }
            }, {
                Timber.tag(TAG).e(it)
                _layoutState.value = LayoutState.NoInternet
            })
            .addTo(disposable)
    }

    fun nextGif() {
        if (_latestGifList.value.size > position + 1) {
            position++
            _currentGif.value = _latestGifList.value[position]
        } else {
            getGifList(++page)
            position = 0
        }
        _backButtonState.value = BackButtonState.Enabled
        Timber.i("PAGE $page  position $position")
    }

    fun prevGif() {
        if (position in 1.._latestGifList.value.size) {
            position--
            _currentGif.value = _latestGifList.value[position]
        } else if (page != 0) {
            getGifList(--page)
            position = _latestGifList.value.size - 1
        }
        if (page == 0 && position == 0) {
            _backButtonState.value = BackButtonState.Disabled
        }
        Timber.i("PAGE $page  position $position")
    }

    fun setGifFromCurrentPosition() {
        if (_latestGifList.value.isNotEmpty()) {
            _layoutState.value = LayoutState.ShowGifViewer
            _currentGif.value = _latestGifList.value[position]
        }
    }

    fun setNoInternetState() {
        _layoutState.value = LayoutState.NoInternet
    }

    sealed class LayoutState {
        object ShowGifViewer : LayoutState()
        object NoInternet : LayoutState()
    }

    sealed class BackButtonState {
        object Enabled : BackButtonState()
        object Disabled : BackButtonState()
    }

    companion object {
        private val TAG = LatestGifViewModel::class.java.simpleName
    }
}