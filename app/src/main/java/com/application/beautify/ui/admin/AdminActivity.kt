package com.application.beautify.ui.admin

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.application.beautify.R
import com.application.beautify.common.Constant
import com.application.beautify.common.StoredValue
import com.application.beautify.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_admin.*
import kotlinx.android.synthetic.main.app_bar_admin.*
import android.support.v4.app.Fragment
import com.application.beautify.model.User
import com.application.beautify.ui.admin.fragment.BeautifyFragment
import com.application.beautify.ui.admin.fragment.MapFragment
import com.application.beautify.ui.admin.fragment.PromotionFragment
import com.application.beautify.ui.admin.fragment.UserFragment
import kotlinx.android.synthetic.main.nav_header_admin.view.*

class AdminActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        user = StoredValue(applicationContext).getObject(Constant().PREFS_USER_KEY) as User

        nav_view.setNavigationItemSelectedListener(this)

        val menu = nav_view.menu
        val item = menu.findItem(R.id.nav_beautify)
        item.isChecked = true
        title = item.title
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.flContent, BeautifyFragment::class.java.newInstance()).commit()

        val headerLayout = nav_view.getHeaderView(0)
        headerLayout.textViewNavUserUsername.text = user.username
        headerLayout.textViewNavUserEmail.text = user.email
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.admin, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                StoredValue(applicationContext).removeValue(Constant().PREFS_USER_KEY)
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null
        var fragmentClass: Class<*>? = null
        when (item.itemId) {
            R.id.nav_beautify -> {
                fragmentClass = BeautifyFragment::class.java
            }
            R.id.nav_profile -> {
                fragmentClass = UserFragment::class.java
            }
            R.id.nav_promotion -> {
                fragmentClass = PromotionFragment::class.java
            }
            R.id.nav_map -> {
                fragmentClass = MapFragment::class.java
            }
            else -> {
                fragmentClass = BeautifyFragment::class.java
            }
        }

        try {
            fragment = fragmentClass!!.newInstance() as Fragment
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment!!).commit()

        item.isChecked = true
        title = item.title

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

}
