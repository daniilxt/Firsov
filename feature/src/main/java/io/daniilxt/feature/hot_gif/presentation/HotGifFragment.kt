package io.daniilxt.feature.hot_gif.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import io.daniilxt.common.di.FeatureUtils
import io.daniilxt.common.extensions.*
import io.daniilxt.feature.R
import io.daniilxt.feature.databinding.FragmentHotGifBinding
import io.daniilxt.feature.di.FeatureApi
import io.daniilxt.feature.di.FeatureComponent
import io.daniilxt.feature.domain.model.GifModel
import io.daniilxt.helper.GlideHelper
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class HotGifFragment : Fragment() {

    @Inject
    lateinit var viewModel: HotGifViewModel

    private var _binding: FragmentHotGifBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        inject()
        viewModel.initDatabase(requireContext())
        _binding = FragmentHotGifBinding.inflate(inflater, container, false)
        viewModel.getGifList()

        binding.frgHotGifGifViewer.includeGifViewerIbNext.setDebounceClickListener {
            viewModel.nextGif()
        }
        binding.frgHotGifGifViewer.includeGifViewerIbBack.setDebounceClickListener {
            viewModel.prevGif()
        }
        binding.frgHotGifAlertError.includeNoInternetConnectionMbWarning.setDebounceClickListener {
            if (requireActivity().isOnline(requireContext())) {
                viewModel.setGifFromCurrentPosition()
            }
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
            viewModel.hotGifList.collect {
                viewModel.setGifFromCurrentPosition()
            }
        }
        lifecycleScope.launch {
            viewModel.layoutState.collect {
                setLayout(it)
            }
        }
    }

    private fun handleBackButtonState(state: HotGifViewModel.BackButtonState) {
        when (state) {
            is HotGifViewModel.BackButtonState.Disabled -> disableBackButton()
            is HotGifViewModel.BackButtonState.Enabled -> enableBackButton()
        }
    }

    private fun enableBackButton() {
        binding.frgHotGifGifViewer.includeGifViewerIbBack.isClickable = true
    }

    private fun disableBackButton() {
        binding.frgHotGifGifViewer.includeGifViewerIbBack.isClickable = false
    }

    private fun setLayout(state: HotGifViewModel.LayoutState) {
        disableIncludedLayouts()
        when (state) {
            is HotGifViewModel.LayoutState.ShowGifViewer -> setShowGifViewerLayout()
            is HotGifViewModel.LayoutState.NoInternet -> setNoInternetLayout()
        }
    }

    private fun setGifWithInfo(gifModel: GifModel) {
        with(binding.frgHotGifGifViewer) {
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
            requireContext(), path, binding.frgHotGifGifViewer.includeGifViewerIvImage
        ) { viewModel.setNoInternetState() }
    }

    private fun disableIncludedLayouts() {
        binding.frgHotGifGifViewer.root.visibility = View.GONE
        binding.frgHotGifAlertError.root.visibility = View.GONE
    }

    private fun setShowGifViewerLayout() {
        binding.frgHotGifGifViewer.root.visibility = View.VISIBLE
    }

    private fun setNoInternetLayout() {
        binding.frgHotGifAlertError.root.visibility = View.VISIBLE
    }

    private fun inject() {
        FeatureUtils.getFeature<FeatureComponent>(this, FeatureApi::class.java)
            .hotGifComponentFactory()
            .create(this)
            .inject(this)
    }
}