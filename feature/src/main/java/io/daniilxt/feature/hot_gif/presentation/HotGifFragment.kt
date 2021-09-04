package io.daniilxt.feature.hot_gif.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.daniilxt.common.di.FeatureUtils
import io.daniilxt.common.extensions.clearLightStatusBar
import io.daniilxt.common.extensions.setStatusBarColor
import io.daniilxt.feature.R
import io.daniilxt.feature.databinding.FragmentHotGifBinding
import io.daniilxt.feature.di.FeatureApi
import io.daniilxt.feature.di.FeatureComponent
import ru.daniilxt.feature.hot_gif.presentation.HotGifViewModel
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
        _binding = FragmentHotGifBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().setStatusBarColor(R.color.white)
        requireView().clearLightStatusBar()
    }

    private fun inject() {
        FeatureUtils.getFeature<FeatureComponent>(this, FeatureApi::class.java)
            .hotGifComponentFactory()
            .create(this)
            .inject(this)
    }
}