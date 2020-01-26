package com.alex.helpietest.model.api

import com.alex.helpietest.model.Photo
import com.alex.helpietest.model.Post
import com.alex.helpietest.model.User
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ServerApi {

    private val retrofit: Retrofit

    init {

        val httpClient = OkHttpClient.Builder()

        val gson = GsonBuilder().setLenient().create()

        //Configuração do Retrofit
        this.retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient.build())
            .build()
    }

    //Configuração das funções para consumir os dados da url utlizando Rxjava(Observables) e Retrofit
    fun loadUser(): Observable<User> {
        val service = retrofit.create(UserApiDef::class.java)

        return service.listUsers()
            .flatMap { userResult ->
                val userResultSorted = userResult.sortedBy { it.name }
                Observable.fromIterable(userResultSorted) }
    }

    fun loadPhotos(): Observable<Photo>{
        val service = retrofit.create(PhotoApiDef::class.java)

        return service.listPhotos()
            .flatMap { photoResult -> Observable.fromIterable(photoResult) }
    }

    fun loadPost(id: String): Observable<Post> {
        val service = retrofit.create(PostApiDef::class.java)

        return service.listPost(id)
            .flatMap { postResult -> Observable.fromIterable(postResult)}
    }
}