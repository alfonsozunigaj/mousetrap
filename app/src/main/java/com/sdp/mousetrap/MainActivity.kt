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
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v4.app.FragmentManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), FragmentDelegate {

    private lateinit var mDrawerLayout: DrawerLayout
    private val Preferences_name : String = "Prefs"
    private var Preferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Preferences = this.getSharedPreferences(Preferences_name, Context.MODE_PRIVATE)

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
            else if (menuItem.itemId == R.id.nav_profile) {
                val transaction = manager.beginTransaction()
                transaction.replace(R.id.main_frame, UserFragment())
                transaction.addToBackStack(null)
                transaction.commit()
            }
            else if (menuItem.itemId == R.id.nav_logout) {
                val editor = Preferences!!.edit()
                editor.clear()
                editor.commit()
                navigationView.getMenu().getItem(0).setChecked(true)
                loadHome(true)
                val intent = Intent(this, Login::class.java)
                startActivityForResult(intent,1)
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

    override fun onStart() {
        super.onStart()
        val token : String = Preferences!!.getString("token", "0")
        if (token=="0"){
            val intent = Intent(this, Login::class.java)
            startActivityForResult(intent,1)
        }
        else {
            //Toast.makeText(this, "Welcome $token !!", Toast.LENGTH_SHORT).show()
        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if ((requestCode == 1) && (resultCode == RESULT_OK)) {
            val token = data!!.extras.getString("token")
            val id = data!!.extras.getInt("id")
            val email = data!!.extras.getString("email")
            val username = data!!.extras.getString("username")
            val editor = Preferences!!.edit()
            editor.putString("token", token)
            editor.putInt("id", id)
            editor.putString("email", email)
            editor.putString("username", username)
            editor.commit()
        }
    }
}
