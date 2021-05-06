package com.github.zharovvv.traveler.ui

import android.os.Bundle
import com.github.zharovvv.traveler.R
import com.github.zharovvv.traveler.ui.screen.CitiesFragment

class MainActivity : AndroidXMvpAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_container, CitiesFragment(), "mainFragment")
                .commit()
        }
    }
}