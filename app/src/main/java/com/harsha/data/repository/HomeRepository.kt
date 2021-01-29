package com.harsha.data.repository

import com.harsha.common.Constants.KEYWORD
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Query

interface HomeRepository {
    fun getStoreList(@Query(KEYWORD) search: String): Single<Response<ResponseBody>>

}