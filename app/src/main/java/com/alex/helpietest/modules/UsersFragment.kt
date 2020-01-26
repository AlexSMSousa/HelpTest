package com.alex.helpietest.modules


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.alex.helpietest.adapters.AdapterRecyclerViewUser
import com.alex.helpietest.adapters.ItemUserClickListener
import com.alex.helpietest.R
import com.alex.helpietest.model.User
import com.alex.helpietest.model.api.ServerApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_users.*




class UsersFragment : Fragment(), ItemUserClickListener {

    private val listUser = mutableListOf<User>()

    private val compositeDisposable = CompositeDisposable()

    companion object{
        const val CODE_USER_ID = "CODE_USER_INTENT"
        const val CODE_USER_NAME = "CODE_USER_NAME_INTENT"
    }

    //Tratamento do click realizado no item do RecyclerView
    override fun onItemClick(user: User) {
        val intent = Intent(requireContext(), PostsActivity::class.java)
        intent.putExtra(CODE_USER_ID, user.id)
        intent.putExtra(CODE_USER_NAME, user.name)
        startActivity(intent)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViewUsers()
    }


    private fun setupRecyclerViewUsers(){

        val context = this@UsersFragment.context

        val adapterRecyclerViewUser =
            AdapterRecyclerViewUser(listUser, this)

        //Configuração do RecyclerView
        listUsers_rv_fragmentUsers.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            itemAnimator = DefaultItemAnimator()
            adapter = adapterRecyclerViewUser

        }

        //Utilização do Rxjava para consumir os dados recebidos do end point e adicioná-los ao RecyclerView
        val api = ServerApi()
        val disposable = api.loadUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    user -> listUser.add(user)
            },{
                    e -> e.printStackTrace()
            },{
                    adapterRecyclerViewUser.notifyDataSetChanged()
            })

        compositeDisposable.add(disposable)

    }

    override fun onStop() {
        this.compositeDisposable.clear()
        super.onStop()
    }




}

