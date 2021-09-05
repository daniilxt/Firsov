package io.daniilxt.feature.main_screen.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import io.daniilxt.common.di.FeatureUtils
import io.daniilxt.common.extensions.setDebounceClickListener
import io.daniilxt.common.extensions.setLightStatusBar
import io.daniilxt.common.extensions.setStatusBarColor
import io.daniilxt.feature.R
import io.daniilxt.feature.databinding.FragmentMainScreenBinding
import io.daniilxt.feature.di.FeatureApi
import io.daniilxt.feature.di.FeatureComponent
import io.daniilxt.feature.hot_gif.presentation.HotGifFragment
import io.daniilxt.feature.latest_gif.presentation.LatestGifFragment
import io.daniilxt.feature.main_screen.presentation.adapter.MainScreenViewPagerAdapter
import io.daniilxt.feature.top_gif.presentation.TopGifFragment
import javax.inject.Inject

class MainScreenFragment : Fragment() {

    @Inject
    lateinit var viewModel: MainScreenViewModel

    private var _binding: FragmentMainScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainScreenViewPagerAdapter: MainScreenViewPagerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        inject()
        _binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        binding.frgMainScreenToolbar.includeTabLayoutToolbarIbProfile.setDebounceClickListener {
            viewModel.openProfileFragment()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().setStatusBarColor(R.color.white)
        requireView().setLightStatusBar()
        initViewPager()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun initViewPager() {
        mainScreenViewPagerAdapter = MainScreenViewPagerAdapter(
            this, listOf(LatestGifFragment(), TopGifFragment(), HotGifFragment())
        )
        binding.frgMainScreenViewPager.adapter = mainScreenViewPagerAdapter
        val titles = listOf(
            getString(R.string.latest), getString(R.string.top), getString(R.string.hot)
        )

        TabLayoutMediator(
            binding.frgMainScreenToolbar.includeTabLayout, binding.frgMainScreenViewPager
        ) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    private fun inject() {
        FeatureUtils.getFeature<FeatureComponent>(this, FeatureApi::class.java)
            .mainScreenComponentFactory()
            .create(this)
            .inject(this)
    }
}