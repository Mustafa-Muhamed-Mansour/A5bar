package com.a5bar.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.a5bar.R
import com.a5bar.breakingNews.BreakingNewsViewModel
import com.a5bar.breakingNews.BreakingNewsViewModelFactory
import com.a5bar.databinding.ActivityMainBinding
import com.a5bar.network.local.NewsDB
import com.a5bar.repository.BreakingNewsRepository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
//    lateinit var viewModel: BreakingNewsViewModel
//    lateinit var newsViewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)

        supportActionBar?.hide()

//        val navController = setupNavController()
//        attachNavUIToController(navController)

//        navController = NavController(applicationContext)
//        Navigation.findNavController(this, R.id.nav_host_fragment_container)
//        setupWithNavController(binding.bottomNavView, navController = NavController(this))
//        navController.addOnDestinationChangedListener { controller, destination, arguments ->
//
//            when (destination.id)
//            {
////                R.id.splashFragment, R.id.loginFragment, R.id.registerFragment, R.id.forgetPasswordFragment -> binding.bottomNav.visibility = View.GONE
////                else -> binding.bottomNav.visibility = View.VISIBLE
//            }
//
//        }

//        val breakingNewsRepository = BreakingNewsRepository(NewsDB(applicationContext))
//        val breakingNewsViewModelFactory =
//            BreakingNewsViewModelFactory(application, breakingNewsRepository)
//        viewModel =
//            ViewModelProvider(this, breakingNewsViewModelFactory)[BreakingNewsViewModel::class.java]

//        val save = SavedNewsRepository(NewsDB(applicationContext))
//        val newsViewModelFactory = NewsFactory(save)
//        newsViewModel = ViewModelProvider(this, newsViewModelFactory)[NewsViewModel::class.java]

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
//        navHostFragment.navController
//
        binding.bottomNavView.setupWithNavController(navHostFragment.navController)

        navHostFragment.navController.addOnDestinationChangedListener { controller, destination, arguments ->

            when (destination.id) {
                R.id.splashFragment -> binding.bottomNavView.visibility = View.GONE
                else -> binding.bottomNavView.visibility = View.VISIBLE
            }

        }

//        navController = NavController(applicationContext)
//        navController = Navigation.findNavController(this, R.id.nav_host_fragment_container)
//        setupWithNavController(binding.bottomNavView, navController)
//        navController.addOnDestinationChangedListener { controller, destination, arguments ->
//
//            when (destination.id)
//            {
//                R.id.articleNewsFragment -> binding.bottomNavView.visibility = View.GONE
//                else -> binding.bottomNavView.visibility = View.VISIBLE
//            }

//        }

    }

//    private fun attachNavUIToController(navController: NavController) {
//        val navView: BottomNavigationView = binding.bottomNavView
//
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.breakingNewsFragment,
//                R.id.savedNewsFragment,
//                R.id.searchNewsFragment,
//            )
//        )
//
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
//    }
//
//    private fun setupNavController(): NavController {
//        val navHostFragment = supportFragmentManager
//            .findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
//        return navHostFragment.navController
//    }

}