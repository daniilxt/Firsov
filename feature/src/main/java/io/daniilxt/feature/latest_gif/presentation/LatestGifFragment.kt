package io.daniilxt.feature.latest_gif.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import io.daniilxt.common.di.FeatureUtils
import io.daniilxt.common.extensions.*
import io.daniilxt.feature.R
import io.daniilxt.feature.databinding.FragmentLatestGifBinding
import io.daniilxt.feature.di.FeatureApi
import io.daniilxt.feature.di.FeatureComponent
import io.daniilxt.feature.domain.model.GifModel
import io.daniilxt.helper.GlideHelper
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class LatestGifFragment : Fragment() {

    @Inject
    lateinit var viewModel: LatestGifViewModel

    private var _binding: FragmentLatestGifBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        inject()
        viewModel.initDatabase(requireContext())
        _binding = FragmentLatestGifBinding.inflate(inflater, container, false)
        viewModel.getGifList()

        binding.frgLatestGifGifViewer.includeGifViewerIbNext.setDebounceClickListener {
            viewModel.nextGif()
        }
        binding.frgLatestGifGifViewer.includeGifViewerIbBack.setDebounceClickListener {
            viewModel.prevGif()
        }
        binding.frgLatestGifAlertError.includeNoInternetConnectionMbWarning.setDebounceClickListener {
            if (requireActivity().isOnline(requireContext())) {
                viewModel.setGifFromCurrentPosition()
            }
        }
        binding.frgLatestGifNoData.includeNoDataMbTryAgain.setDebounceClickListener {
            viewModel.setGifFromCurrentPosition()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().setStatusBarColor(R.color.white)
        requireView().clearLightStatusBar()

        subscribe()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun subscribe() {
        lifecycleScope.launch {
            viewModel.backButtonState.collect {
                handleBackButtonState(it)
            }
        }
        lifecycleScope.launch {
            viewModel.currentGif.collect {
                it?.let { it1 -> setGifWithInfo(it1) }
            }
        }
        // Set image when fragment started
        lifecycleScope.launch {
            viewModel.latestGifList.collect {
                viewModel.setGifFromCurrentPosition()
            }
        }
        lifecycleScope.launch {
            viewModel.layoutState.collect {
                setLayout(it)
            }
        }
    }

    private fun handleBackButtonState(state: LatestGifViewModel.BackButtonState) {
        when (state) {
            is LatestGifViewModel.BackButtonState.Disabled -> disableBackButton()
            is LatestGifViewModel.BackButtonState.Enabled -> enableBackButton()
        }
    }

    private fun enableBackButton() {
        binding.frgLatestGifGifViewer.includeGifViewerIbBack.isClickable = true
    }

    private fun disableBackButton() {
        binding.frgLatestGifGifViewer.includeGifViewerIbBack.isClickable = false
    }

    private fun setLayout(state: LatestGifViewModel.LayoutState) {
        disableIncludedLayouts()
        when (state) {
            is LatestGifViewModel.LayoutState.ShowGifViewer -> setShowGifViewerLayout()
            is LatestGifViewModel.LayoutState.NoInternet -> setNoInternetLayout()
            is LatestGifViewModel.LayoutState.NoData -> setNoDataLayout()
        }
    }

    private fun setGifWithInfo(gifModel: GifModel) {
        with(binding.frgLatestGifGifViewer) {
            setImage(gifModel.gifURL)
            includeGifViewerTvDescription.showAnimatedText(gifModel.description)
            includeGifViewerStatistics.includeGifStatisticsTvAuthor.showAnimatedText(gifModel.author)
            includeGifViewerStatistics.includeGifStatisticsTvComments.showAnimatedText(
                gifModel.commentsCount.toString()
            )
            includeGifViewerStatistics.includeGifStatisticsTvLike.showAnimatedText(gifModel.votes.toString())
        }
    }

    private fun setImage(path: String) {
        GlideHelper.loadGif(
            requireContext(), path, binding.frgLatestGifGifViewer.includeGifViewerIvImage
        ) { viewModel.setNoInternetState() }
    }

    private fun disableIncludedLayouts() {
        binding.frgLatestGifGifViewer.root.visibility = View.GONE
        binding.frgLatestGifAlertError.root.visibility = View.GONE
        binding.frgLatestGifNoData.root.visibility = View.GONE
    }

    private fun setShowGifViewerLayout() {
        binding.frgLatestGifGifViewer.root.visibility = View.VISIBLE
    }

    private fun setNoInternetLayout() {
        binding.frgLatestGifAlertError.root.visibility = View.VISIBLE
    }

    private fun setNoDataLayout() {
        binding.frgLatestGifNoData.root.visibility = View.VISIBLE
    }

    private fun inject() {
        FeatureUtils.getFeature<FeatureComponent>(this, FeatureApi::class.java)
            .latestGifComponentFactory()
            .create(this)
            .inject(this)
    }
}