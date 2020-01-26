package com.alex.helpietest.modules


import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.alex.helpietest.adapters.AdapterRecyclerViewPhoto
import com.alex.helpietest.adapters.ItemPhotoClickListener
import com.alex.helpietest.R
import com.alex.helpietest.model.Photo
import com.alex.helpietest.model.api.ServerApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_photos.*

import android.widget.Toast


class PhotosFragment : Fragment(), ItemPhotoClickListener {

    private val compositeDisposable = CompositeDisposable()

    companion object{
        const val CODE_PHOTO = "CODE_PHOTO_INTENT"
    }

    //Tratamento do click realizado no item do RecyclerView
    override fun onItemClick(photo: Photo) {
        val intent = Intent(requireContext(), ExpandedImageActivity::class.java)
        intent.putExtra(CODE_PHOTO, photo.url)
        startActivity(intent)
    }

    private val listPhoto = mutableListOf<Photo>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_photos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setupRecyclerViewPhotos(2)
        }else{
            setupRecyclerViewPhotos(3)
        }
    }


    private fun setupRecyclerViewPhotos(columns: Int){

        val context = this@PhotosFragment.context

        val adapterRecyclerViewPhoto =
            AdapterRecyclerViewPhoto(listPhoto, this)

        //Configuração do RecyclerView
        listPhotos_rv_fragmentPhotos.apply {
            layoutManager = GridLayoutManager(context, columns)
            itemAnimator = DefaultItemAnimator()
            adapter = adapterRecyclerViewPhoto
        }


        //Utilização do Rxjava para consumir os dados recebidos do end point e adicioná-los ao RecyclerView
        val api = ServerApi()
        val disposable = api.loadPhotos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    photo -> listPhoto.add(photo)
            },{
                    e -> e.printStackTrace()
            },{
                adapterRecyclerViewPhoto.notifyDataSetChanged()
            })

        compositeDisposable.add(disposable)

    }

    override fun onStop() {
        this.compositeDisposable.clear()
        super.onStop()
    }


}
