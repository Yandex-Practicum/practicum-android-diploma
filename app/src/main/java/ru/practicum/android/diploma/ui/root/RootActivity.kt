package ru.practicum.android.diploma.ui.root

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import org.koin.android.ext.android.inject
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.network.token.AccessTokenProvider
import ru.practicum.android.diploma.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {

    private val tokenProvider: AccessTokenProvider by inject()
    private val binding: ActivityRootBinding by lazy { ActivityRootBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        deleteAllDatabases()
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        authRequest(accessToken = BuildConfig.HH_ACCESS_TOKEN)
        observeKeyboardVisibility()
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val view = currentFocus
        if (ev.action == MotionEvent.ACTION_DOWN && view is EditText) {
            val outRect = Rect()
            view.getGlobalVisibleRect(outRect)
            if (!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                view.clearFocus()
                hideKeyboard(view)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun observeKeyboardVisibility() {
        val rootView = binding.root
        rootView.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            rootView.getWindowVisibleDisplayFrame(rect)
            val screenHeight = rootView.rootView.height
            val keypadHeight = screenHeight - rect.height()

            val isKeyboardVisible = keypadHeight > screenHeight * SCREEN_PROPORTION
            val bottomNav = binding.bottomNavigationView

            bottomNav.animate()
                .translationY(if (isKeyboardVisible) bottomNav.height.toFloat() else 0f)
                .setDuration(ANIM_DURATION)
                .start()
        }
    }

    private fun hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun authRequest(accessToken: String) {
        tokenProvider.saveAccessToken(accessToken)
        val restoredToken = tokenProvider.getAccessToken()
        Log.d("RootActivity", "Token restored from prefs: $restoredToken")
    }

    private fun deleteAllDatabases() {
        this.deleteDatabase("FavoriteVacanciesDatabase.db")
    }

    companion object {
        private const val ANIM_DURATION = 200L
        private const val SCREEN_PROPORTION = 0.15
    }
}
