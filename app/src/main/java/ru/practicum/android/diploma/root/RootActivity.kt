package ru.practicum.android.diploma.root

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

import androidx.lifecycle.ViewModelProvider
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.app.App
import ru.practicum.android.diploma.di.ViewModelFactory
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {
    private val component by lazy {
        (application as App).component
            .activityComponentFactory()
            .create()
    }

    @Inject
    lateinit var logger: Logger

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[RootViewModel::class.java]
    }
    private val binding by lazy { ActivityRootBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        logger.log(thisName, "onCreate() -> Unit $logger")
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.rootFragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            logger.log(
                thisName,
                "addOnDestinationChangedListener { destination = ${destination.label} }"
            )
            when (destination.id) {

                R.id.filterBaseFragment -> hideBottomNav()
                R.id.detailsFragment -> hideBottomNav()

                else -> showBottomNav()
            }
        }

        // Пример использования access token для HeadHunter API
        networkRequestExample(accessToken = BuildConfig.HH_ACCESS_TOKEN)
        viewModel.doSmth("hello From Activity $viewModel")
    }

    private fun networkRequestExample(accessToken: String) {
        // ...
    }

    private fun hideBottomNav() {
        logger.log(thisName, "hideBottomNav()")
        binding.bottomNavigationView.visibility = View.GONE
    }

    private fun showBottomNav() {
        logger.log(thisName, "showBottomNav()")
        binding.bottomNavigationView.visibility = View.VISIBLE
    }

}