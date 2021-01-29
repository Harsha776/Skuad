package com.harsha.di

import com.harsha.di.scope.ActivityScoped
import com.harsha.ui.home.HomeActivity
import com.harsha.ui.splashactivity.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(
    includes = [ViewModelModule::class]
)
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector
    abstract fun splashActivity(): SplashActivity

    @ActivityScoped
    @ContributesAndroidInjector
    abstract fun homeActivity(): HomeActivity
}