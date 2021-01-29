package com.harsha.ui.splashactivity

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.harsha.ui.home.HomeActivity
import com.harsha.ui.splashactivity.databinding.SplashActivityBinding
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class SplashActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var mBinding:SplashActivityBinding
    lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeView()
        initializeViewModel()
        runAnimation()
        dalayObserver()
    }

    /**
     * this method is used to initialize the view
     */
    private fun initializeView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.splash_activity)
    }

    /**
     * This method is used initialize the viewModel class for this activity.
     */
    private fun initializeViewModel() {
        splashViewModel = ViewModelProviders.of(this, viewModelFactory).get(SplashViewModel::class.java)
        mBinding.viewModel=splashViewModel
        splashViewModel.delayScreen()
    }

    /**
     * text animation
     */
    private fun runAnimation() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        mBinding.tvSplashTitle.startAnimation(animation)
    }

    /**
     * observing the delay to move next activity
     */
    private fun dalayObserver(){
        splashViewModel.onDelayObserver().observe(this, Observer {
            if(it){
                var intent= Intent(this,HomeActivity::class.java)
                startActivity(intent)
            }
        })
    }
}