package com.aku.app_kchttp_sample.api

import androidx.navigation.Navigation
import com.aku.app_kchttp_sample.data.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * wanAndroid Api
 */
interface WanApi {

    companion object {
        const val BASE_URL = ""
    }

    /**
     * 首页文章列表
     */
    @GET("article/list/{pageNo}/json")
    suspend fun getArticles(@Path("pageNo") pageNo: Int): HttpResponse<Page<Article>>

    /**
     * 置顶文章列表
     */
    @GET("article/top/json")
    suspend fun getTopArticles(): HttpResponse<List<Article>>

    /**
     * 首页 banner
     */
    @GET("banner/json")
    suspend fun getBanner(): HttpResponse<List<Banner>>

    /**
     * 公众号列表
     */
    @GET("wxarticle/chapters/json")
    suspend fun getWXChapters(): HttpResponse<List<Chapter>>

    /**
     * 查看某个公众号历史数据
     */
    @GET("wxarticle/list/{id}/{pageNo}/json")
    suspend fun getWXArticles(@Path("id") id: Int, @Path("pageNo") pageNo: Int)
            : HttpResponse<Page<Article>>

    /**
     * 项目类目列表
     */
    @GET("project/tree/json")
    suspend fun getProjects(): HttpResponse<List<Chapter>>

    /**
     * 项目文章列表
     */
    @GET("project/list/{pageNo}/json")
    suspend fun getProjectArticles(@Path("pageNo") pageNo: Int, @Query("cid") cid: Int): HttpResponse<Page<Article>>

    /**
     * 导航数据
     */
    @GET("navi/json")
    suspend fun getProjectArticles(): HttpResponse<List<Navigation>>

    /**
     * 知识体系
     */
    @GET("tree/json")
    suspend fun getKnowledgeTree(): HttpResponse<List<Chapter>>

    /**
     * 知识体系文章列表
     */
    @GET("article/list/{pageNo}/json")
    suspend fun getKnowledgeArticles(
        @Path("pageNo") pageNo: Int,
        @Query("cid") cid: Int
    ): HttpResponse<Page<Article>>

}
