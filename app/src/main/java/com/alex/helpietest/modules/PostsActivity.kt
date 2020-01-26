package com.alex.helpietest.modules

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.alex.helpietest.adapters.AdapterRecyclerViewPost
import com.alex.helpietest.R
import com.alex.helpietest.model.Post
import com.alex.helpietest.model.api.ServerApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_posts.*

class PostsActivity : AppCompatActivity() {

    private val listPost = mutableListOf<Post>()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

        val idUser: Long = intent.getLongExtra(UsersFragment.CODE_USER_ID, 1)
        val nameUser: String? = intent.getStringExtra(UsersFragment.CODE_USER_NAME)

        nameUser.let { title = "$nameUser - Posts" }

        setupRecyclerViewPost(idUser.toString())
    }


    private fun setupRecyclerViewPost(idString: String){

        val adapterRecyclerViewPost = AdapterRecyclerViewPost(listPost)

        //Configuração do RecyclerView
        listPosts_rv_fragmentPosts.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            itemAnimator = DefaultItemAnimator()
            adapter = adapterRecyclerViewPost

        }

//Utilização do Rxjava para consumir os dados recebidos do end point e adicioná-los ao RecyclerView
        val api = ServerApi()
        val disposable = api.loadPost(idString)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    post -> listPost.add(post)
            },{
                    e -> e.printStackTrace()
            },{
                adapterRecyclerViewPost.notifyDataSetChanged()
            })

        compositeDisposable.add(disposable)

    }

    override fun onStop() {
        this.compositeDisposable.clear()
        super.onStop()
    }
}
