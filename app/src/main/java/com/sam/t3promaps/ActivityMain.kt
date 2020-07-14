package com.sam.t3promaps

import MainMapsFragment
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager

class ActivityMain : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var supportFragment = supportFragmentManager
        var transacsion = supportFragment.beginTransaction()
        transacsion.add(R.id.main_content,MainMapsFragment())
        transacsion.commit()

//        supportFragmentManager.beginTransaction()
//            .add(R.id.main_content, MainMapsFragment()).commit()

    }
}