package com.application.beautify.ui.user

import android.Manifest
import android.annotation.TargetApi
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.application.beautify.R
import com.application.beautify.common.Constant
import com.application.beautify.common.StoredValue
import com.application.beautify.ui.login.LoginActivity
import com.application.beautify.ui.user.fragment.UserFavFragment
import com.application.beautify.ui.user.fragment.UserHomeFragment
import com.application.beautify.ui.user.fragment.UserMapFragment
import com.application.beautify.ui.user.fragment.UserSearchFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_user.*

class UserActivity : AppCompatActivity() {

    private val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        title = "หน้าหลัก"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (arePermissionsEnabled()) {
                initUI()
            } else {
                requestMultiplePermissions()
            }
        }
    }

    private fun initUI() {
        navigation.background = ColorDrawable(ContextCompat.getColor(applicationContext, R.color.navOne))
        navigation.itemBackground = ColorDrawable(ContextCompat.getColor(applicationContext, R.color.navOne))
        var fragment: Fragment = UserHomeFragment()
        loadFragment(fragment)

        navigation.setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener {

            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.navigation_home -> {
                        navigation.background =
                            ColorDrawable(ContextCompat.getColor(applicationContext, R.color.navOne))
                        navigation.itemBackground =
                            ColorDrawable(ContextCompat.getColor(applicationContext, R.color.navOne))
                        title = "หน้าหลัก"
                        fragment = UserHomeFragment()
                        loadFragment(fragment)
                        return true
                    }
                    R.id.navigation_search -> {
                        navigation.background =
                            ColorDrawable(ContextCompat.getColor(applicationContext, R.color.navTwo))
                        navigation.itemBackground =
                            ColorDrawable(ContextCompat.getColor(applicationContext, R.color.navTwo))
                        title = "ค้นหา"
                        fragment = UserSearchFragment()
                        loadFragment(fragment)
                        return true
                    }
                    R.id.navigation_map -> {
                        navigation.background =
                            ColorDrawable(ContextCompat.getColor(applicationContext, R.color.navThree))
                        navigation.itemBackground =
                            ColorDrawable(ContextCompat.getColor(applicationContext, R.color.navThree))
                        fragment = UserMapFragment()
                        loadFragment(fragment)
                        title = "แผนที่"

                        return true
                    }
                    R.id.navigation_fav -> {
                        navigation.background =
                            ColorDrawable(ContextCompat.getColor(applicationContext, R.color.navFour))
                        navigation.itemBackground =
                            ColorDrawable(ContextCompat.getColor(applicationContext, R.color.navFour))
                        title = "รายการโปรด"
                        fragment = UserFavFragment()
                        loadFragment(fragment)
                        return true
                    }
                }
                return false
            }
        })
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val login = menu?.findItem(R.id.action_login)
        val logout = menu?.findItem(R.id.action_logout)

        if (FirebaseAuth.getInstance().currentUser != null) {
            login?.isVisible = false
            logout?.isVisible = true
        } else {
            login?.isVisible = true
            logout?.isVisible = false
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.user, menu)
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
            R.id.action_login -> {
                startActivity(Intent(this, LoginActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun arePermissionsEnabled(): Boolean {
        for (permission in permissions) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED)
                return false
        }
        return true
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun requestMultiplePermissions() {
        val remainingPermissions = arrayListOf<String>()
        for (permission in permissions) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                remainingPermissions.add(permission)
            }
        }
        requestPermissions(remainingPermissions.toTypedArray(), 101)
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101) {
            for (i in grantResults.indices) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    if (shouldShowRequestPermissionRationale(permissions[i])) {
                        AlertDialog.Builder(this)
                            .setMessage("This application needs Location and Storage permissions to work without any problems")
                            .setPositiveButton("Allow") { dialog, which -> requestMultiplePermissions() }
                            .setNegativeButton("Cancel") { dialog, which -> dialog.dismiss() }
                            .create()
                            .show()
                    }
                    return
                }
            }
            initUI()
        }
    }
}
