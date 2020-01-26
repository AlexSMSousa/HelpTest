package com.alex.helpietest.modules

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.alex.helpietest.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),  BottomNavigationView.OnNavigationItemSelectedListener {

    private var indexSelected = -1
    private val CODE_PERSIST = "CODE_PERSIST_PAG"

    //Lista contendo os fragments
    private val contents = arrayListOf(
        UsersFragment(),
        PhotosFragment()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView_activityMain.setOnNavigationItemSelectedListener(this)

        if(savedInstanceState != null){
            val pag = savedInstanceState.getInt(CODE_PERSIST)
            addContent(pag)
        }else{
            addContent(0)
        }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(CODE_PERSIST, indexSelected)

    }

    //Função para alternar entre os fragments
    private fun addContent(index: Int){
        if(index == indexSelected){
            return
        }

        title = if(index == 0) getString(R.string.users) else getString(R.string.photos)

        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(R.id.content_activityMain, contents[index])
            .commit()

        indexSelected = index
    }

    //Criação do menu inferior
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {

        return when (p0.itemId){
            R.id.menu_users ->{
                addContent(0)
                true
            }

            R.id.menu_photos ->{
                addContent(1)
                true
            }

            else->{
                false
            }
        }
    }

}
