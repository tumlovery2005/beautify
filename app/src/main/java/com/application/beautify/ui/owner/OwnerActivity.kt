package com.application.beautify.ui.owner

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.application.beautify.R
import com.application.beautify.common.Constant
import com.application.beautify.common.StoredValue
import com.application.beautify.common.onClick
import com.application.beautify.ui.login.LoginActivity
import com.application.beautify.ui.owner.manage.OwnerManageActivity
import com.application.beautify.ui.owner.promotion.OwnerPromotionActivity
import com.application.beautify.ui.owner.queue.OwnerQueueActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_owner.*

class OwnerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner)

        initUI()
    }

    private fun initUI() {
        btnOwnerCheckQueue.onClick {
            startActivity(Intent(this, OwnerQueueActivity::class.java))
        }
        btnOwnerManage.onClick {
            startActivity(Intent(this, OwnerManageActivity::class.java))
        }
        btnOwnerPromotion.onClick {
            startActivity(Intent(this, OwnerPromotionActivity::class.java))
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
}
