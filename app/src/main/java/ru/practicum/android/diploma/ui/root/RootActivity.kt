package ru.practicum.android.diploma.ui.root

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {
    private var _binding: ActivityRootBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        _binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.root_container) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, windowInsets ->
            val insents = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                leftMargin = insents.left
                topMargin = insents.top
                bottomMargin = insents.bottom
                rightMargin = insents.right
            }
            WindowInsetsCompat.CONSUMED
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.vacancy_details_fragment -> {
                    binding.bottomNav.visibility = View.GONE
                    binding.bottomNavView.visibility = View.GONE
                    systemBottomPadding(APPLY_BOTTOM_PADDING)
                }

                else -> {
                    binding.bottomNav.visibility = View.VISIBLE
                    binding.bottomNavView.visibility = View.VISIBLE
                    systemBottomPadding(REMOVE_BOTTOM_PADDING)
                }
            }
        }
    }

    private fun systemBottomPadding(boolean: Boolean) {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                if (boolean) systemBars.bottom else 0
            )
            insets
        }
    }

    companion object {
        private const val APPLY_BOTTOM_PADDING = true
        private const val REMOVE_BOTTOM_PADDING = false
    }
}
