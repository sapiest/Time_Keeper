package com.timekeeper

import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.os.Bundle
import com.example.toxaxab.timekeeper.R
import com.timekeeper.UI.Navigation.ActivityAct
import com.timekeeper.UI.Navigation.SettingsAct
import com.timekeeper.UI.Navigation.StatisticsAct

import kotlinx.android.synthetic.main.activity_main.*

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter
        container.offscreenPageLimit = 2
        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))
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
            when (position) {
                0 -> {
                    return resources.getString(R.string.tab_1)
                }
                1 -> {
                    return resources.getString(R.string.tab_2)
                }
                2 -> {
                    return resources.getString(R.string.tab_3)
                }
                else -> return null
            }
        }
    }
    /**
     * A placeholder fragment containing a simple view.
     */

}
