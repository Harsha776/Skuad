package com.harsha.data.repositoryImpl

import com.harsha.data.RepoService
import com.harsha.data.repository.HomeRepository
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response

class HomeRepositoryImpl(
    private val repoService: RepoService
) : HomeRepository {
    override fun getStoreList(search: String): Single<Response<ResponseBody>> =repoService.getStoreList(search)
}