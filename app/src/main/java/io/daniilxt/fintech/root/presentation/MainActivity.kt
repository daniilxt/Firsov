package io.daniilxt.fintech.root.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import io.daniilxt.common.di.FeatureUtils
import io.daniilxt.fintech.R
import io.daniilxt.fintech.databinding.ActivityMainBinding
import io.daniilxt.fintech.navigation.Navigator
import io.daniilxt.fintech.root.di.RootApi
import io.daniilxt.fintech.root.di.RootComponent
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var activityViewModel: MainActivityViewModel

    @Inject
    lateinit var navigator: Navigator

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inject()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        val navController = navHostFragment.navController
        navigator.attach(navController, this)

    }

    override fun onDestroy() {
        super.onDestroy()
        navigator.detach()
    }
    private fun inject() {
        FeatureUtils.getFeature<RootComponent>(this, RootApi::class.java)
            .mainActivityComponentFactory()
            .create(this)
            .inject(this)
    }
}