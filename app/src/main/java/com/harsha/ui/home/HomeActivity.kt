package com.harsha.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.harsha.common.Constants.ERROR
import com.harsha.common.Constants.INTERNET_ISSUE
import com.harsha.common.Utility
import com.harsha.data.model.ResponseData
import com.harsha.ui.splashactivity.R
import com.harsha.ui.splashactivity.databinding.HomeActivityBinding
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomeActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var mBinding:HomeActivityBinding
    lateinit var homeViewModel: HomeViewModel
    lateinit var storeAdapter: StoreAdapter
    lateinit var layoutManager: LinearLayoutManager
    val storedata=ArrayList<ResponseData.Result>()
    lateinit var subject: PublishSubject<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeView()
        initializeViewModel()
        checkInternetState()
        getStoreData()
    }

    /**
     * this method is used to initialize the view
     */
    private fun initializeView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.home_activity)
        //Textwater for the EditText
        mBinding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                subject.onNext(s.toString())
            }
        })

        //using the debounce operator for edit text delay
        subject= PublishSubject.create()
        subject.debounce(1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Subject<String?>() {
                override fun hasObservers(): Boolean {
                    return false
                }

                override fun hasThrowable(): Boolean {
                    return false
                }

                override fun hasComplete(): Boolean {
                    return false
                }

                override fun getThrowable(): Throwable? {
                    return null
                }

                override fun onSubscribe(d: Disposable) {}

                override fun onNext(s: String) {
                    Utility.hideSoftKeyboard(this@HomeActivity)
                    homeViewModel.getStoreDetails(s)
                }

                override fun onError(e: Throwable) {}
                override fun onComplete() {}
                override fun subscribeActual(observer: io.reactivex.Observer<in String?>?) {
                }
            })
    }

    /**
     * This method is used initialize the viewModel class for this activity.
     */
    private fun initializeViewModel() {
        homeViewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
        mBinding.viewModel=homeViewModel
        layoutManager = LinearLayoutManager(this)
        homeViewModel.getStoreDetails("")
        storeAdapter= StoreAdapter(storedata)
        mBinding.rvStoreList.layoutManager=layoutManager
        mBinding.rvStoreList.adapter=storeAdapter
    }

    private fun getStoreData(){
        homeViewModel.getstoreDataList().observe(this, Observer {
            if(it!=null && it.isNotEmpty()){
                storedata.clear()
                storedata.addAll(it)
                storeAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun checkInternetState(){
        homeViewModel.getErrorMessage().observe(this, Observer {
            when(it.first){
                INTERNET_ISSUE->showInternetError()
                ERROR-> responseIssue(it.second as String)
            }
        })
    }

    private fun showInternetError() {
        AlertDialog.Builder(this)
            .setMessage(getString(R.string.noInternetError))
            .setPositiveButton(getString(R.string.retry)) { _, _ -> homeViewModel.getStoreDetails("") }
            .setOnCancelListener { homeViewModel.getStoreDetails("") }
            .show()
    }

    private fun responseIssue(error:String){
        Toast.makeText(this,error,Toast.LENGTH_SHORT).show()
    }

}