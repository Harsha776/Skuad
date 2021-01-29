package com.harsha.ui.splashactivity

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.harsha.common.Constants.SPLASH_DELAY_MILLIS
import com.harsha.common.Constants.TRUE
import javax.inject.Inject


class SplashViewModel @Inject constructor():ViewModel() {

    private val _time = MutableLiveData<Boolean>()

     fun delayScreen(){
         Handler(Looper.getMainLooper()).postDelayed({
             _time.postValue(TRUE)
         }, SPLASH_DELAY_MILLIS)
    }

    fun onDelayObserver():MutableLiveData<Boolean>{
        return _time
    }
}