package com.harsha.di

import androidx.lifecycle.ViewModel
import com.harsha.di.scope.ViewModelKey
import com.harsha.ui.home.HomeViewModel
import com.harsha.ui.splashactivity.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule : ViewModelFactoryModule() {

    /*
    * This method basically says
    * inject this object into a Map using the @IntoMap annotation,
    * with the  LoginViewModel.class as key,
    * and a Provider that will build a LoginViewModel
    * object.
    *
    * */
    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun splashViewModel(splashViewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun homeViewModel(homeViewModel: HomeViewModel): ViewModel

}