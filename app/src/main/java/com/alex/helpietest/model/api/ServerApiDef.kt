package com.alex.helpietest.model.api

import com.alex.helpietest.model.Photo
import com.alex.helpietest.model.Post
import com.alex.helpietest.model.User
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

//Definição das funções e dos metodos HTTP que serão utlizados bem como seus endpoints
interface UserApiDef {
    @GET("users")
    fun listUsers(): Observable<List<User>>
}

interface PhotoApiDef{
    @GET("photos")
    fun listPhotos(): Observable<List<Photo>>
}

interface PostApiDef{
    @GET("posts")
    fun listPost(
        @Query("userId")uid: String
    ): Observable<List<Post>>


}

