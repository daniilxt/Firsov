package io.daniilxt.feature.top_gif.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import io.daniilxt.common.di.FeatureUtils
import io.daniilxt.common.extensions.*
import io.daniilxt.feature.R
import io.daniilxt.feature.databinding.FragmentTopGifBinding
import io.daniilxt.feature.di.FeatureApi
import io.daniilxt.feature.di.FeatureComponent
import io.daniilxt.feature.domain.model.GifModel
import io.daniilxt.helper.GlideHelper
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class TopGifFragment : Fragment() {

    @Inject
    lateinit var viewModel: TopGifViewModel

    private var _binding: FragmentTopGifBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        inject()
        viewModel.initDatabase(requireContext())
        _binding = FragmentTopGifBinding.inflate(inflater, container, false)
        viewModel.getGifList()

        binding.frgTopGifGifViewer.includeGifViewerIbNext.setDebounceClickListener {
            viewModel.nextGif()
        }
        binding.frgTopGifGifViewer.includeGifViewerIbBack.setDebounceClickListener {
            viewModel.prevGif()
        }
        binding.frgTopGifAlertError.includeNoInternetConnectionMbWarning.setDebounceClickListener {
            if (requireActivity().isOnline(requireContext())) {
                viewModel.setGifFromCurrentPosition()
            }
        }
        binding.frgTopGifNoData.includeNoDataMbTryAgain.setDebounceClickListener {
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
            viewModel.topGifList.collect {
                viewModel.setGifFromCurrentPosition()
            }
        }
        lifecycleScope.launch {
            viewModel.layoutState.collect {
                setLayout(it)
            }
        }
    }

    private fun handleBackButtonState(state: TopGifViewModel.BackButtonState) {
        when (state) {
            is TopGifViewModel.BackButtonState.Disabled -> disableBackButton()
            is TopGifViewModel.BackButtonState.Enabled -> enableBackButton()
        }
    }

    private fun enableBackButton() {
        binding.frgTopGifGifViewer.includeGifViewerIbBack.isClickable = true
    }

    private fun disableBackButton() {
        binding.frgTopGifGifViewer.includeGifViewerIbBack.isClickable = false
    }

    private fun setLayout(state: TopGifViewModel.LayoutState) {
        disableIncludedLayouts()
        when (state) {
            is TopGifViewModel.LayoutState.ShowGifViewer -> setShowGifViewerLayout()
            is TopGifViewModel.LayoutState.NoInternet -> setNoInternetLayout()
            is TopGifViewModel.LayoutState.NoData -> setNoDataLayout()
        }
    }

    private fun setGifWithInfo(gifModel: GifModel) {
        with(binding.frgTopGifGifViewer) {
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
            requireContext(), path, binding.frgTopGifGifViewer.includeGifViewerIvImage
        ) { viewModel.setNoInternetState() }
    }

    private fun disableIncludedLayouts() {
        binding.frgTopGifGifViewer.root.visibility = View.GONE
        binding.frgTopGifAlertError.root.visibility = View.GONE
    }

    private fun setShowGifViewerLayout() {
        binding.frgTopGifGifViewer.root.visibility = View.VISIBLE
    }

    private fun setNoInternetLayout() {
        binding.frgTopGifAlertError.root.visibility = View.VISIBLE
    }

    private fun setNoDataLayout() {
        binding.frgTopGifNoData.root.visibility = View.VISIBLE
    }

    private fun inject() {
        FeatureUtils.getFeature<FeatureComponent>(this, FeatureApi::class.java)
            .topGifComponentFactory()
            .create(this)
            .inject(this)
    }
}