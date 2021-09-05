package io.daniilxt.feature.profile.di

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import io.daniilxt.common.di.scope.ScreenScope
import io.daniilxt.feature.profile.presentation.ProfileFragment

@Subcomponent(
    modules = [
        ProfileModule::class,
    ]
)
@ScreenScope
interface ProfileComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: Fragment): ProfileComponent
    }

    fun inject(profileFragment: ProfileFragment)
}