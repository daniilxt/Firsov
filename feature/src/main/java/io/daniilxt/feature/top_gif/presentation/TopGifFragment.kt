package io.daniilxt.feature.top_gif.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.daniilxt.common.di.FeatureUtils
import io.daniilxt.common.extensions.clearLightStatusBar
import io.daniilxt.common.extensions.setStatusBarColor
import io.daniilxt.feature.R
import io.daniilxt.feature.databinding.FragmentTopGifBinding
import io.daniilxt.feature.di.FeatureApi
import io.daniilxt.feature.di.FeatureComponent
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
        _binding = FragmentTopGifBinding.inflate(inflater, container, false)
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
            .topGifComponentFactory()
            .create(this)
            .inject(this)
    }
}