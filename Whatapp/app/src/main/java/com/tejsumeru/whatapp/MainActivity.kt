package com.tejsumeru.whatapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.tejsumeru.whatapp.Adapter.TabSwitcher
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()
{
    var firebaseAuth : FirebaseAuth ?=null
    var currentUser : FirebaseUser ? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(main_toolbar as Toolbar?)
        supportActionBar?.title = "Whatsapp"

        firebaseAuth = FirebaseAuth.getInstance()
        currentUser = firebaseAuth!!.currentUser

        main_view_pager.adapter = TabSwitcher(supportFragmentManager)

        tab_switcher.setupWithViewPager(main_view_pager)

    }

    override fun onStart() {
        super.onStart()

        if (currentUser == null)
        {
            sendToLoginActivity()
        }
    }

    private fun sendToLoginActivity() {
        var intent = Intent(this@MainActivity,LoginActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)

        menuInflater.inflate(R.menu.option_menu,menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)

        if (item.itemId == R.id.option_find_friend )
        {

        }
        else if (item.itemId == R.id.option_settings)
        {
            sendToSettingsActivity()
        }
        else if (item.itemId == R.id.option_logout)
        {
            firebaseAuth!!.signOut()
            sendToLoginActivity()
        }

        return true
    }

    private fun sendToSettingsActivity() {
        var intent = Intent(this@MainActivity,SettingsActivity::class.java)
        startActivity(intent)
    }
}