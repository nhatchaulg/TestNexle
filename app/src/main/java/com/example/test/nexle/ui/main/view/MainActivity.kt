package com.example.test.nexle.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.test.nexle.R
import com.example.test.nexle.databinding.ActivityMainBinding
import com.example.test.nexle.ui.auth.view.AuthActivity
import com.example.test.nexle.ui.main.viewmodel.MainViewModel
import com.example.test.nexle.utils.Status
import com.example.test.nexle.utils.UtilsSharePreference
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = _binding.root
        setContentView(view)
        setupView()
    }

    private fun setupView() {
        _binding.btnMenu.setOnClickListener {
            showMenu()
        }

        _binding.txNameUser.text = UtilsSharePreference.getUserName()

        actionBarDrawerToggle = ActionBarDrawerToggle(this, _binding.drawLayout, R.string.nav_open, R.string.nav_close)

        _binding.drawLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        _binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_logout -> {
                    viewModel.logout().observe(this) {
                        when (it.status) {
                            Status.SUCCESS -> {
                                _binding.layoutLoading.visibility = View.GONE
                                Toast.makeText(this, "Logout Success", Toast.LENGTH_LONG).show()
                                gotoLogin()
                            }
                            Status.ERROR -> {
                                _binding.layoutLoading.visibility = View.GONE
                                Toast.makeText(this, "Logout Fail", Toast.LENGTH_LONG).show()
                            }
                            Status.LOADING -> {
                                _binding.layoutLoading.visibility = View.VISIBLE
                            }
                        }
                    }

                    true
                }
                else -> false
            }
        }
    }

    private fun gotoLogin() {
        UtilsSharePreference.updateUserToken("")
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showMenu() {
        _binding.drawLayout.open()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }
}