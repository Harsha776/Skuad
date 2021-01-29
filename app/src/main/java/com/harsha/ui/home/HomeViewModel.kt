package com.harsha.ui.home

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.harsha.common.Constants.ERROR
import com.harsha.common.Constants.INTERNET_ISSUE
import com.harsha.common.NoConnectivityException
import com.harsha.data.model.ResponseData
import com.harsha.data.repository.HomeRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject


class HomeViewModel @Inject constructor( private val homeRepository: HomeRepository):ViewModel() {

     val progressBar=ObservableBoolean(false)
    val storeData=MutableLiveData<List<ResponseData.Result>>()
    val errorData=MutableLiveData<Pair<String,*>>()

    /**
     * <h2>getStoreDetails</h2>
     * this is the method to get store response from Api
     */
    fun getStoreDetails(search:String) {
        progressBar.set(true)
        val disposableObserver =
                object : DisposableSingleObserver<Response<ResponseBody>>() {
                    override fun onSuccess(value: Response<ResponseBody>) {
                        progressBar.set(false)
                        val response: String = value.body()!!.string()
                        val gson = Gson()
                        val responseData = gson.fromJson(response, ResponseData::class.java)
                        storeData.postValue(responseData.results)
                    }

                    override fun onError(e: Throwable) {
                        progressBar.set(false)
                        if (e is NoConnectivityException) {
                            errorData.postValue(Pair(INTERNET_ISSUE,e.message))
                        }
                        else {
                            errorData.postValue(Pair(ERROR,e.message))
                        }
                    }
                }
        homeRepository.getStoreList(search).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver)

    }

   fun getstoreDataList():MutableLiveData<List<ResponseData.Result>> {
       return storeData;
   }

    fun getErrorMessage():MutableLiveData<Pair<String,*>>{
        return errorData
    }
}