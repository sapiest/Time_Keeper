package com.timekeeper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.os.Bundle
import android.os.SystemClock
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.toxaxab.timekeeper.R
import com.timekeeper.Adapters.ActivityActAdapter
import com.timekeeper.Database.Entity.Activity
import com.timekeeper.Database.Entity.Status
import com.timekeeper.UI.Navigation.ActivityTab.ActivityAct
import com.timekeeper.UI.Navigation.ActivityTab.NewActivity
import com.timekeeper.UI.Navigation.SettingsTab.SettingsAct
import com.timekeeper.UI.Navigation.StatisticsTab.StatisticsAct

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item.view.*
import java.util.*


class MainActivity : AppCompatActivity() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    private var fm: FragmentManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fm = supportFragmentManager
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(fm!!)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        container.offscreenPageLimit = 2
        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewActivity::class.java)
            startActivityForResult(intent, StatisticsAct.newActivityRequestCode)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val act = intent?.action
        if (act == "STOP") {
            val data = intent.getIntExtra("activity", -1)
            val viewId = intent.getIntExtra("viewId", -1)
            Log.i("ID", data.toString())
            val actActivity = fm!!.fragments[0] as ActivityAct
            actActivity.adapter.holder.stopTimer(viewId, actActivity.adapter.statuses[data])
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)
        if (requestCode == StatisticsAct.newActivityRequestCode && resultCode == android.app.Activity.RESULT_OK) {
            intentData?.let { data ->
                val info = data.getStringArrayListExtra(NewActivity.EXTRA_REPLY)
                val actFragment = fm!!.fragments[0] as ActivityAct
                var id = 0
                //TODO УБЕРИ КОСТЫЛЬ
                if (actFragment.activityViewModel.allActivity.value != null) {
                    id = actFragment.activityViewModel.allActivity.value!!.size
                }
                val status = Status(id, 0, 0, 0)
                val act = Activity(status.id, info[0], info[1], status.id)
                actFragment.insert(status, act)
            }
        } else {
            Toast.makeText(applicationContext, "НЕЧЕГО СОХРАНЯТЬ", Toast.LENGTH_LONG).show()
        }
    }

    //TO DEBUG
    /*override fun onStart() {
        super.onStart()

        Toast.makeText(applicationContext, "onStart()", Toast.LENGTH_SHORT).show()
        Log.i("MAIN", "onStart()")
    }


    override fun onResume() {
        super.onResume()

        Toast.makeText(applicationContext, "onResume()", Toast.LENGTH_SHORT).show()
        Log.i("MAIN", "onResume()")
    }


    override fun onPause() {
        super.onPause()

        Toast.makeText(applicationContext, "onPause()", Toast.LENGTH_SHORT).show()
        Log.i("MAIN", "onPause()")
    }

    override fun onStop() {
        super.onStop()

        Toast.makeText(applicationContext, "onStop()", Toast.LENGTH_SHORT).show()
        Log.i("MAIN", "onStop()")
    }

    override fun onRestart() {
        super.onRestart()

        Toast.makeText(applicationContext, "onRestart()", Toast.LENGTH_SHORT).show()
        Log.i("MAIN", "onRestart()")
    }

    override fun onDestroy() {
        super.onDestroy()

        Toast.makeText(applicationContext, "onDestroy()", Toast.LENGTH_SHORT).show()
        Log.i("MAIN", "onDestroy()")
    }
    //!TO DEBUG*/

    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */

    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment? {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return when (position) {
                0 -> {
                    ActivityAct()
                }
                1 -> {
                    StatisticsAct()
                }
                2 -> {
                    SettingsAct()
                }
                else -> null
            }
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 3
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when (position) {
                0 -> {
                    resources.getString(R.string.tab_1)
                }
                1 -> {
                    resources.getString(R.string.tab_2)
                }
                2 -> {
                    resources.getString(R.string.tab_3)
                }
                else -> null
            }
        }
    }
    /**
     * A placeholder fragment containing a simple view.
     */

}
