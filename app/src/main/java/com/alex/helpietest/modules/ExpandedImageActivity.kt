package com.alex.helpietest.modules

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alex.helpietest.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_expanded_image.*

class ExpandedImageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expanded_image)

        //Recebimento da 'url' da photo
        val urlPhoto = intent.getStringExtra(PhotosFragment.CODE_PHOTO)

        //Carregamento da 'url' de photo
        Picasso.with(this)
            .load(urlPhoto)
            .into(photo_img_activityExpanded)

    }
}
