package io.daniilxt.feature.latest_gif.presentation

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import io.daniilxt.common.di.FeatureUtils
import io.daniilxt.common.extensions.*
import io.daniilxt.feature.R
import io.daniilxt.feature.databinding.FragmentLatestGifBinding
import io.daniilxt.feature.di.FeatureApi
import io.daniilxt.feature.di.FeatureComponent
import io.daniilxt.feature.domain.model.GifModel
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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
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
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().setStatusBarColor(R.color.white)
        requireView().clearLightStatusBar()

        subscribe()
    }

    private fun subscribe() {
        lifecycleScope.launch {
            viewModel.currentGif.collect {
                it?.let { it1 -> setGifWithInfo(it1) }
            }
        }
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

    private fun setLayout(state: LatestGifViewModel.LayoutState) {
        disableIncludedLayouts()
        when (state) {
            is LatestGifViewModel.LayoutState.ShowGifViewer -> setShowGifViewerLayout()
            is LatestGifViewModel.LayoutState.NoInternet -> setNoInternetLayout()
        }
    }

    private fun setGifWithInfo(gifModel: GifModel) {
        with(binding.frgLatestGifGifViewer) {
            setImage2(gifModel.gifURL)
            includeGifViewerTvDescription.showAnimatedText(gifModel.description)
            includeGifViewerStatistics.includeGifStatisticsTvAuthor.showAnimatedText(gifModel.author)
            includeGifViewerStatistics.includeGifStatisticsTvComments.showAnimatedText(
                gifModel.commentsCount.toString()
            )
            includeGifViewerStatistics.includeGifStatisticsTvLike.showAnimatedText(gifModel.votes.toString())
        }
    }

    private fun setImage2(path: String) {
        val circularProgressDrawable = CircularProgressDrawable(requireContext())
        circularProgressDrawable.strokeWidth = 8f
        circularProgressDrawable.centerRadius = 40f
        circularProgressDrawable.setColorSchemeColors(Color.BLUE)
        circularProgressDrawable.start()

        Glide.with(this)
            .load(path)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Timber.i("GLIDE ERROR ${e.toString()}")
                    circularProgressDrawable.stop()
                    viewModel.setNoInternetState()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    dataSource: com.bumptech.glide.load.DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            })
            .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
            .placeholder(circularProgressDrawable)
            .into(binding.frgLatestGifGifViewer.includeGifViewerIvImage)
    }

    private fun disableIncludedLayouts() {
        binding.frgLatestGifGifViewer.root.visibility = View.GONE
        binding.frgLatestGifAlertError.root.visibility = View.GONE
    }

    private fun setShowGifViewerLayout() {
        binding.frgLatestGifGifViewer.root.visibility = View.VISIBLE
    }

    private fun setNoInternetLayout() {
        binding.frgLatestGifAlertError.root.visibility = View.VISIBLE
    }

    private fun inject() {
        FeatureUtils.getFeature<FeatureComponent>(this, FeatureApi::class.java)
            .latestGifComponentFactory()
            .create(this)
            .inject(this)
    }
}