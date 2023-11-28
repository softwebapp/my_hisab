package com.my.myhisab.ui

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import com.my.myhisab.R
import com.my.myhisab.databinding.ActivityMainBinding
import com.my.myhisab.ui.fragment.HomeFragment
import com.my.myhisab.ui.fragment.UserFragment
import com.my.utils.extensions.toast

class MainActivity : BaseActivity() {

    private var doubleBackToExitPressedOnce = false

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadFragment(HomeFragment())

//        loader.show()

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    loadFragment(HomeFragment())
                    true
                }

                R.id.user -> {
                    loadFragment(UserFragment())
                    true
                }

                else -> {
                    true
                }
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }


    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce){
            super.onBackPressed()
            finishAffinity()
        }
        doubleBackToExitPressedOnce = true
        toast("Please click BACK again to exit")
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }

}