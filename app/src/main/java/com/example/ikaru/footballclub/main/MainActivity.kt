package com.example.ikaru.footballclub.main

import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.ikaru.footballclub.Event.EventFragment
import com.example.ikaru.footballclub.R
import com.example.ikaru.footballclub.R.id.last_nav
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var id = "4328"
    private var navbar = 1
    private var savedInstanceState: Bundle? = null

//    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener OnNavigationItemSelectedListener@{ item ->
            when (item.itemId) {
                R.id.last_nav -> {
                    navbar = 1
                    createFragment(EventFragment.newInstance(navbar, id))
                    return@OnNavigationItemSelectedListener true
                }
                R.id.next_nav -> {
                    navbar = 2
                    createFragment(EventFragment.newInstance(navbar, id))
                    return@OnNavigationItemSelectedListener true
                }
            }
            false

        }

        navigation.selectedItemId = last_nav
    }


    private fun createFragment(fragment: Fragment){
        if(savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment, fragment.javaClass.simpleName)
                .commit()
        }
    }



}
