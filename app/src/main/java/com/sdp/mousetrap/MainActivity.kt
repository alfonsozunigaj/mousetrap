package com.sdp.mousetrap

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import java.util.*
import android.R.attr.fragment
import android.support.v4.app.FragmentManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), FragmentDelegate {

    private lateinit var mDrawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDrawerLayout = findViewById(R.id.drawer_layout) as DrawerLayout

        val navigationView: NavigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            val manager = supportFragmentManager
            if (menuItem.itemId == R.id.nav_home) {
                loadHome(true)
            }
            else if (menuItem.itemId == R.id.nav_polls) {
                val transaction = manager.beginTransaction()
                transaction.replace(R.id.main_frame, PollsFragment())
                transaction.addToBackStack(null)
                transaction.commit()
            }
            else if (menuItem.itemId == R.id.nav_prices) {
                val transaction = manager.beginTransaction()
                transaction.replace(R.id.main_frame, AwardsFragment())
                transaction.addToBackStack(null)
                transaction.commit()
            }
            mDrawerLayout.closeDrawers()
            true
        }

        loadHome(false)

        val toolbar: Toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        mDrawerLayout.addDrawerListener(
                object : DrawerLayout.DrawerListener {
                    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                        // Respond when the drawer's position changes
                    }

                    override fun onDrawerOpened(drawerView: View) {
                        // Respond when the drawer is opened
                    }

                    override fun onDrawerClosed(drawerView: View) {
                        // Respond when the drawer is closed
                    }

                    override fun onDrawerStateChanged(newState: Int) {
                        // Respond when the drawer motion state changes
                    }
                }
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                mDrawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun createFragmentManager(): FragmentManager {
        return supportFragmentManager
    }

    fun loadHome(condition: Boolean) {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.main_frame, HomeFragment())
        if (condition) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

    fun getEmojiByUnicode(unicode: Int): String {
        return String(Character.toChars(unicode))
    }
}
