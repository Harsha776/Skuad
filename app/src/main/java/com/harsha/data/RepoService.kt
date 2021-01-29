package com.harsha.data

import com.harsha.ui.splashactivity.BuildConfig
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RepoService {

    companion object {
        const val LAT_LONG = "47.6204,-122.3491"
        const val RADIUS = "2500"
        const val RESTAURANT = "restaurant"
        const val KEYWORD = "keyword"
    }

    @GET("maps/api/place/nearbysearch/json?location=${LAT_LONG}&radius=${RADIUS}&type=${RESTAURANT}&key=${BuildConfig.API_key}")
    fun getStoreList(@Query(KEYWORD) search: String): Single<Response<ResponseBody>>

}