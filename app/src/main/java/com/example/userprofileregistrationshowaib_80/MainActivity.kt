package com.example.userprofileregistrationshowaib_80


import android.content.Intent
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var listButton: Button
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        drawerLayout = findViewById(R.id.drawerLayout)
        val navigationView: NavigationView = findViewById(R.id.navigationView)


        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        listButton = findViewById(R.id.profileListBtn)
        listButton.setOnClickListener {
            openLoadingActivity()
        }


        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.profileMenuItem -> {

                    loadFragment(ProfileFragment())
                    drawerLayout.closeDrawers()
                    true
                }
                R.id.profileListMenuItem -> {

                    openLoadingActivity()
                    drawerLayout.closeDrawers()
                    true
                }
                else -> false
            }
        }
    }


    private fun openLoadingActivity() {
        val intent = Intent(this, LoadingActivity::class.java)
        intent.putExtra("TARGET_ACTIVITY", "com.example.userprofileregistrationshowaib_80.ProfileListActivity")
        startActivity(intent)

    }


    private fun loadFragment(fragment: ProfileFragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onBackPressed() {

        if (drawerLayout.isDrawerOpen(androidx.core.view.GravityCompat.START)) {
            drawerLayout.closeDrawer(androidx.core.view.GravityCompat.START)
        } else {

            moveTaskToBack(true)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
