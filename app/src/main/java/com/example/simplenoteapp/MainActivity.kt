package com.example.simplenoteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(HomeFragment.newInstance(), false)
    }

    fun replaceFragment(fragment: Fragment, isTransition: Boolean) {
        val fragTransition = supportFragmentManager.beginTransaction()

        if (isTransition)
            fragTransition.setCustomAnimations(android.R.anim.slide_out_right, android.R.anim.slide_in_left)

        fragTransition.replace(R.id.frame_layout, fragment).addToBackStack(fragment.javaClass.simpleName).commit()
    }
}