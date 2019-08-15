package com.timekeeper

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.timekeeper.Navigation_Fragment.DashboardFragment
import com.timekeeper.Navigation_Fragment.MainFragment
import com.timekeeper.Navigation_Fragment.SettingsFragment
import kotlinx.android.synthetic.main.activity_navigation.*

class NavigationActivity : AppCompatActivity() {
    private val main = MainFragment()
    private val dashboard = DashboardFragment()
    private val settings = SettingsFragment()

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val selectedFragment by lazy {
            when (item.itemId) {
                R.id.navigation_home -> {
                    main
                }
                R.id.navigation_info -> {
                    dashboard
                }
                R.id.navigation_settings -> {
                    settings
                }
                else -> null
            }
        }

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, selectedFragment!!).commit()
        return@OnNavigationItemSelectedListener true
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, main).commit()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (main.adapter != null) {
            val adapter = main.adapter
            adapter!!.saveStates(outState)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if(main.adapter != null){
            val adapter = main.adapter
            adapter!!.restoreStates(savedInstanceState)
        }
    }
}
